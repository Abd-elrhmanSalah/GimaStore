package com.gima.gimastore.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gima.gimastore.entity.Notification;
import com.gima.gimastore.entity.User;
import com.gima.gimastore.entity.UserPrivileges;
import com.gima.gimastore.model.NotificationDTO;
import com.gima.gimastore.repository.NotificationRepository;
import com.gima.gimastore.repository.UserPrivilegesRepository;
import com.gima.gimastore.repository.UserRepository;
import com.gima.gimastore.util.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class NotificationService {
    private NotificationRepository notificationRepo;
    private SimpMessagingTemplate messagingTemplate;
    private UserRepository userRepo;
    private UserPrivilegesRepository userPrivilegesRepo;

    public NotificationService(NotificationRepository notificationRepo, SimpMessagingTemplate messagingTemplate, UserRepository userRepo, UserPrivilegesRepository userPrivilegesRepo) {
        this.notificationRepo = notificationRepo;
        this.messagingTemplate = messagingTemplate;
        this.userRepo = userRepo;
        this.userPrivilegesRepo = userPrivilegesRepo;
    }

    public void addNotification(NotificationDTO notificationDTO) {

        notificationRepo.save(ObjectMapperUtils.map(notificationDTO, Notification.class));
        notifyFrontend(Long.parseLong(notificationDTO.getReadBy()));
    }

    public void notifyFrontend(Long userId) {
//        Notification response;
        User user = userRepo.findById(userId).get();
        List<String> privileges = getPrivilegesByUser(user);

        List<Notification> response = notificationRepo.findAllByPrivilegeAndCreatedByNot(privileges, user);
        messagingTemplate.convertAndSendToUser(Long.toString(userId), "/topic/private-notification", response);
//        messagingTemplate.convertAndSend("/topic/message", response);


    }

    public void sendNotification(Notification notification, String userId) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(notification);
        messagingTemplate.convertAndSendToUser(userId, "/queue/notifications", json);
    }

    private List<String> getPrivilegesByUser(User user) {
        UserPrivileges userPrivileges = userPrivilegesRepo.findByUser(user).get();

        List<String> privileges = new ArrayList<>();

        if (userPrivileges.getHaveUser())
            privileges.add("haveUser");

        if (userPrivileges.getAddUser())
            privileges.add("addUser");

        if (userPrivileges.getEditUser())
            privileges.add("editUser");

        if (userPrivileges.getBlockUser())
            privileges.add("blockUser");

        if (userPrivileges.getHavePart())
            privileges.add("havePart");

        if (userPrivileges.getAddPart())
            privileges.add("addPart");

        if (userPrivileges.getEditPart())
            privileges.add("editPart");

        if (userPrivileges.getBlockPart())
            privileges.add("blockPart");

        if (userPrivileges.getHaveProduct())
            privileges.add("haveProduct");

        if (userPrivileges.getAddProduct())
            privileges.add("addProduct");

        if (userPrivileges.getEditProduct())
            privileges.add("editProduct");

        if (userPrivileges.getBlockProduct())
            privileges.add("blockProduct");

        if (userPrivileges.getHaveStore())
            privileges.add("haveStore");

        if (userPrivileges.getAddStore())
            privileges.add("addStore");

        if (userPrivileges.getEditStore())
            privileges.add("editStore");

        if (userPrivileges.getBlockStore())
            privileges.add("blockStore");

        if (userPrivileges.getHaveSupervisor())
            privileges.add("haveSupervisor");

        if (userPrivileges.getAddSupervisor())
            privileges.add("addSupervisor");

        if (userPrivileges.getEditSupervisor())
            privileges.add("editSupervisor");

        if (userPrivileges.getBlockSupervisor())
            privileges.add("blockSupervisor");

        if (userPrivileges.getHaveDepartment())
            privileges.add("haveDepartment");

        if (userPrivileges.getAddDepartment())
            privileges.add("addDepartment");

        if (userPrivileges.getEditDepartment())
            privileges.add("editDepartment");

        if (userPrivileges.getBlockDepartment())
            privileges.add("blockDepartment");

        if (userPrivileges.getHaveSupplier())
            privileges.add("haveSupplier");

        if (userPrivileges.getAddSupplier())
            privileges.add("addSupplier");

        if (userPrivileges.getEditSupplier())
            privileges.add("editSupplier");

        if (userPrivileges.getHaveDepartment())
            privileges.add("blockSupplier");

        if (userPrivileges.getHaveSupplierProcess())
            privileges.add("haveSupplierProcess");

        if (userPrivileges.getAddSupplierProcess())
            privileges.add("addSupplierProcess");

        if (userPrivileges.getEditSupplierProcess())
            privileges.add("editSupplierProcess");

        if (userPrivileges.getReturnSupplierProcess())
            privileges.add("returnSupplierProcess");

        if (userPrivileges.getViewSupplierProcess())
            privileges.add("viewSupplierProcess");

        if (userPrivileges.getHaveSupplierProcessDist())
            privileges.add("haveSupplierProcessDist");

        if (userPrivileges.getHaveStorePartsDist())
            privileges.add("haveStorePartsDist");

        if (userPrivileges.getHaveProductionProcess())
            privileges.add("haveProductionProcess");

        if (userPrivileges.getAddProductionProcess())
            privileges.add("addProductionProcess");

        if (userPrivileges.getHaveStoreDetails())
            privileges.add("haveStoreDetails");

        if (userPrivileges.getSettlementStore())
            privileges.add("settlementStore");

        if (userPrivileges.getHaveProductOut())
            privileges.add("haveProductOut");

        if (userPrivileges.getEditProductOut())
            privileges.add("editProductOut");

        if (userPrivileges.getAddProductOut())
            privileges.add("addProductOut");

        if (userPrivileges.getHaveReports())
            privileges.add("haveReturns");

        if (userPrivileges.getHaveReports())
            privileges.add("haveReports");

        if (userPrivileges.getHaveDistStoreParts())
            privileges.add("haveDistStoreParts");

        if (userPrivileges.getHaveEntities())
            privileges.add("haveEntities");

        return privileges;
    }
}