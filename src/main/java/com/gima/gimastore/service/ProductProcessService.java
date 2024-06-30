package com.gima.gimastore.service;

import com.gima.gimastore.controller.NotificationController;
import com.gima.gimastore.entity.Notification;
import com.gima.gimastore.entity.Part;
import com.gima.gimastore.entity.Store;
import com.gima.gimastore.entity.StorePart;
import com.gima.gimastore.entity.User;
import com.gima.gimastore.entity.productProcess.*;
import com.gima.gimastore.exception.ApplicationException;
import com.gima.gimastore.exception.StatusResponse;
import com.gima.gimastore.model.productionProcess.*;
import com.gima.gimastore.model.supplyProcess.StoreAmount;
import com.gima.gimastore.repository.*;
import com.gima.gimastore.util.ObjectMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.gima.gimastore.constant.ResponseCodes.*;

@Service
@RequiredArgsConstructor
public class ProductProcessService {
    private final ProductionRequestRepository productionRequestRepo;
    private final ProductionRequestPartsRepository productionRequestPartsRepo;
    private final ProductRepository productRepo;
    private final StoreRepository storeRepo;
    private final ProductPartRepository productPartRepo;
    private final StorePartRepository storePartRepo;
    private final ProductionPartsStoreRequestRepository productionPartsStoreRequestRepo;
    private final NotificationController notificationController;
    private final NotificationRepository notificationRepo;
    private final UserRepository userRepo;
    private final PartRepository partRepo;

    @Transactional
    public void addProductionRequest(ProductionAPIRequest productionAPIRequest) {
        Optional<ProductionRequest> byRequestID = productionRequestRepo.findByRequestID(productionAPIRequest.getProductionRequestDTO().getRequestID());
        if (!byRequestID.isEmpty())
            throw new ApplicationException(new StatusResponse(REPEATED_REQUESTEDID.getCode(), REPEATED_REQUESTEDID.getKey()
                    , REPEATED_REQUESTEDID.getMessage()));

        ProductionRequest productionRequest = ObjectMapperUtils.map(productionAPIRequest.getProductionRequestDTO(), ProductionRequest.class);
        productionRequest.setExactlyProduction(0);
        ProductionRequest savedProductionRequest = productionRequestRepo.save(productionRequest);

        productionAPIRequest.getParts().forEach(partApiRequest -> {
            ProductionRequestParts productionRequestParts = ObjectMapperUtils.map(partApiRequest
                    , ProductionRequestParts.class);

            productionRequestParts.setProductionRequest(savedProductionRequest);
            productionRequestPartsRepo.save(productionRequestParts);
            partApiRequest.getStoreAmounts().forEach(storeAmount -> {

                ProductionPartsStoreRequest partsStoreRequest = new ProductionPartsStoreRequest();
                partsStoreRequest.setStore(storeAmount.getStore());
                partsStoreRequest.setPart(partApiRequest.getPart());
                partsStoreRequest.setProductionRequest(savedProductionRequest);
                partsStoreRequest.setLastUpdatedBy(savedProductionRequest.getCreatedBy());
                partsStoreRequest.setLastUpdateDate(savedProductionRequest.getCreationDate());
                partsStoreRequest.setRequestedAmount(storeAmount.getAmount());
                partsStoreRequest.setOutedAmount(0);
                productionPartsStoreRequestRepo.save(partsStoreRequest);
                //////////////////////////////////////////////////////////////
                //Notification part
                Notification notification = new Notification();
                notification.setTitle("طلب مخرجات اذن صرف");
                User user = userRepo.findById(savedProductionRequest.getCreatedBy().getId()).get();
                Part part = partRepo.findById(partApiRequest.getPart().getId()).get();
                notification.setMessage("قام " +
                        user.getFirstName() +
                        " " + user.getLastName() +
                        " " + "بارسال طلب اخراج " + " كمية" + storeAmount.getAmount() + " من" + " " + part.getPartName() +
                        " من مخزنك خاص برقم صرف" + " " +
                        partsStoreRequest.getProductionRequest().getRequestID());
                notification.setCreationDate(savedProductionRequest.getCreationDate());
                notification.setCreatedBy(savedProductionRequest.getCreatedBy());
                notification.setPrivilege("haveDistStoreParts");
                Store store = storeRepo.findById(storeAmount.getStore().getId()).get();
                notification.setReceiver(store.getUser().getId());
                notification.setRouteName("/store-production-requests");
                notification.setReadBy(savedProductionRequest.getCreatedBy().getId().toString());
                notificationRepo.save(notification);

                notificationController.refreshTunles();
            });

        });

    }

    public Page<ProductionRequest> getProductionRequestsByStore(Long storeId, Pageable pageable) {
        Store store = storeRepo.findById(storeId).get();

        List<ProductionPartsStoreRequest> productionPartsStoreRequests = productionPartsStoreRequestRepo
                .findAllByStoreAndIsFullOut(store, false);

        List<ProductionPartsStoreRequest> requests = productionPartsStoreRequests.stream().
                filter(distinctByKey(p -> p.getProductionRequest())).collect(Collectors.toList());

        List<ProductionRequest> productionRequests = new ArrayList<>();
        requests.forEach(partsStoreRequest -> {
            productionRequests.add(productionRequestRepo.findById(partsStoreRequest.getProductionRequest().getId()).get());
        });
        Collections.sort(productionRequests,
                Comparator.comparingLong(ProductionRequest::getId).reversed());

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), productionRequests.size());

        PageImpl<ProductionRequest> productionRequestPage = new PageImpl<>(productionRequests.subList(start, end), pageable,
                requests.size());

        return productionRequestPage;
    }

    public List<ProductionPartsStoreRequest> getRequestsByStoreAndRequestId(Long storeId, Long requestId) {
        Store store = storeRepo.findById(storeId).get();
        ProductionRequest productionRequest = productionRequestRepo.findById(requestId).get();
        List<ProductionPartsStoreRequest> productionPartsStoreRequest = productionPartsStoreRequestRepo
                .findByStoreAndProductionRequest(store, productionRequest);

        return productionPartsStoreRequest;
    }

    @Transactional
    public void confirmProductionRequestInStore(StorePartProductionRequest request) {
        ProductionPartsStoreRequest productionStoreRequest = productionPartsStoreRequestRepo.findById(request.getId()).get();
        int updatedAmount = request.getOutedAmount() + productionStoreRequest.getOutedAmount();
        productionStoreRequest.setOutedAmount(updatedAmount);
        productionStoreRequest.setLastUpdateDate(request.getLastUpdateDate());
        productionStoreRequest.setLastUpdatedBy(request.getLastUpdatedBy());
        if (updatedAmount == productionStoreRequest.getRequestedAmount())
            productionStoreRequest.setFullOut(true);
        productionPartsStoreRequestRepo.save(productionStoreRequest);
        StorePart storePart = storePartRepo.findByStoreAndPart(productionStoreRequest.getStore(), productionStoreRequest.getPart()).get();
        storePart.setAmount(storePart.getAmount() - request.getOutedAmount());
        storePartRepo.save(storePart);
        List<ProductionPartsStoreRequest> byProductionRequest = productionPartsStoreRequestRepo
                .findByProductionRequest(productionStoreRequest.getProductionRequest());
        boolean allFullOut = byProductionRequest.stream().allMatch(pr -> pr.getFullOut());
        if (allFullOut) {
            ProductionRequest productionRequestById = productionRequestRepo
                    .findById(productionStoreRequest.getProductionRequest().getId()).get();
            productionRequestById.setFullOut(true);
            productionRequestRepo.save(productionRequestById);
        }
        //////////////////////////////////////////////////////////////
        //Notification part
        Part part = partRepo.findById(productionStoreRequest.getPart().getId()).get();
        List<StorePart> storePartByPart = storePartRepo.findStorePartByPart(productionStoreRequest.getPart());
        AtomicReference<Integer> totalAmountsInStore = new AtomicReference<>(0);
        storePartByPart.stream().forEach(byPart -> {
            totalAmountsInStore.set(totalAmountsInStore.get() + byPart.getAmount());
        });

        if (totalAmountsInStore.get() < part.getMinAmount()) {
            Notification notification = new Notification();
            notification.setTitle("انذار بالحد بالأدنى");

            notification.setMessage("أصبحت كمية الجزء" +
                    part.getPartName() + " " + totalAmountsInStore.get() + " " + " وهى أقل من الحد الأدنى" + " " + part.getMinAmount());
            notification.setCreationDate(new Date());
            notification.setCreatedBy(request.getLastUpdatedBy());
            notification.setPrivilege("haveReports");
            notification.setReceiver(null);
            notification.setRouteName(" ");
            notification.setReadBy(request.getLastUpdatedBy().getId().toString());
            notificationRepo.save(notification);

            notificationController.refreshTunles();
        }
    }

    public void confirmProductionRequest(String requestId, Integer exactlyAmount) {
        ProductionRequest productionRequest = productionRequestRepo.findByRequestID(requestId).get();
        productionRequest.setCompleted(true);
        productionRequest.setExactlyProduction(exactlyAmount);
        productionRequestRepo.save(productionRequest);
    }

    @Transactional
    public List<ProductPartResponse> getProductParts(Long productId, Integer expectedAmount) {
        Product product = validateExistProduct(productId).get();
        List<ProductPart> productParts = productPartRepo.findAllByProduct(product);

        List<ProductPartResponse> productPartResponseList = new ArrayList<>();
        productParts.forEach(productPart -> {

            Integer partAmountPerProduct = productPart.getAmount();
            Integer totalAmountRequested = partAmountPerProduct * expectedAmount;

            ProductPartResponse productPartResponse = new ProductPartResponse();

            productPartResponse.setPart(productPart.getPart());
            productPartResponse.setRequestedAmount(totalAmountRequested);

            List<StoreAmount> storeAmountList = new ArrayList<>();
            List<StorePart> storePartByPart = storePartRepo.findStorePartByPart(productPart.getPart());
            storePartByPart.forEach(storePart -> {
                StoreAmount storeAmount = new StoreAmount();
                storeAmount.setAmount(storePart.getAmount());
                storeAmount.setStore(storePart.getStore());
                storeAmountList.add(storeAmount);
            });
            productPartResponse.setStores(storeAmountList);
            productPartResponseList.add(productPartResponse);
        });

        return productPartResponseList;
    }

    public Page<ProductionRequest> getAllProductionRequest(Map<String, String> params, Pageable pageable) {

        Page<ProductionRequest> list = productionRequestRepo.findAll(
                (Specification<ProductionRequest>) (root, query, cb) -> {
                    try {
                        SimpleDateFormat formate = new SimpleDateFormat("dd/MM/yyyy");
                        List<Predicate> predicates = new ArrayList<>();

                        Join<ProductionRequest, Product> productionRequestProductJoin = root.join("product");

                        if (params.containsKey("FromDate"))
                            if (!params.get("FromDate").equals(""))
                                predicates.add(cb.greaterThanOrEqualTo(
                                        root.get("creationDate"), formate.parse(params.get("FromDate"))));

                        if (params.containsKey("ToDate"))
                            if (!params.get("ToDate").equals(""))
                                predicates.add(cb.lessThanOrEqualTo(
                                        root.get("creationDate"), formate.parse(params.get("ToDate"))));

                        if (params.containsKey("requestId"))
                            if (!params.get("requestId").equals(""))
                                predicates.add(cb.equal(root.get("requestID"), params.get("requestId")));

                        if (params.containsKey("productId"))
                            if (!params.get("productId").equals(""))
                                predicates.add(cb.equal(productionRequestProductJoin.get("id"), params.get("productId")));

                        if (params.containsKey("isCompleted"))
                            if (!params.get("isCompleted").equals(""))
                                if (params.get("isCompleted").equalsIgnoreCase("false"))
                                    predicates.add(cb.equal(root.get("isCompleted"), false));
                                else
                                    predicates.add(cb.equal(root.get("isCompleted"), true));

                        if (params.containsKey("isFullOut"))
                            if (!params.get("isFullOut").equals(""))
                                if (params.get("isFullOut").equalsIgnoreCase("false"))
                                    predicates.add(cb.equal(root.get("isFullOut"), false));
                                else
                                    predicates.add(cb.equal(root.get("isFullOut"), true));

                        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }, pageable);

        return list;
    }

    public List<ProductionPartsAPIRequest> getProductPartsByRequestId(String requestId) {
        List<ProductionRequestParts> allByProductionRequest = productionRequestPartsRepo.findAllByProductionRequest(
                productionRequestRepo.findByRequestID(requestId).get());
        List<ProductionPartsAPIRequest> returnedProductPartResponseList = new ArrayList<>();
        allByProductionRequest.forEach(productionRequestParts -> {
            ProductionPartsAPIRequest productPartResponse = new ProductionPartsAPIRequest();

            productPartResponse.setPart(productionRequestParts.getPart());
            productPartResponse.setRequestedAmount(productionRequestParts.getRequestedAmount());

            List<ProductionPartsStoreRequest> byPartAndProductionRequest = productionPartsStoreRequestRepo.findByPartAndProductionRequest(
                    productionRequestParts.getPart()
                    , productionRequestParts.getProductionRequest());

            AtomicReference<Integer> totalOut = new AtomicReference<>(0);
            byPartAndProductionRequest.forEach(partsStoreRequest -> {
                totalOut.set(totalOut.get() + partsStoreRequest.getOutedAmount());
            });
            productPartResponse.setTotalAmountOut(totalOut.get());
            returnedProductPartResponseList.add(productPartResponse);

        });

        return returnedProductPartResponseList;
    }

    private Optional<Product> validateExistProduct(Long id) {
        Optional<Product> productById = productRepo.findById(id);
        if (productById.isEmpty())
            throw new ApplicationException(new StatusResponse(NO_PRODUCT_ID.getCode(), NO_PRODUCT_ID.getKey(),
                    NO_PRODUCT_ID.getMessage()));
        return productById;
    }

    public static <T> java.util.function.Predicate<T> distinctByKey(
            Function<? super T, ?> keyExtractor) {

        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

}
