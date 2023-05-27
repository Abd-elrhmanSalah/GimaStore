/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER. Copyright 1997-2016 NTG Clarity and/or its affiliates. All
 * rights reserved. NTG CLARITY is a leader in delivering network, telecom, IT and infrastructure solutions to network
 * service providers and medium and large enterprises. www.ntgclarity.com The contents of this file are subject to the
 * terms of "NTG Clarity License". You must not use this file except in compliance with the License. You can obtain a
 * copy of the License at http://www.ntgclarity.com/ See the License for the specific language governing permissions and
 * limitations under the License. Contributor(s): The Initial Developer of the Original Software is NTG Clarity . , Inc.
 * Copyright 1997-2016 NTG Clarity. All Rights Reserved. CLASS ApplicationException
 * <h4>Description</h4>
 * <h4>Notes</h4>
 * <h4>References</h4>
 *
 * @author: tali <A HREF="mailto:stageem-dev@ntgclarity.com">Stage Development Team</A>
 * @version Revision: 1.0 Date: Nov 16, 2015 4:14:13 PM
 * @see [String]
 * @see [URL]
 * @see [Class name#method name]
 */

package com.gima.gimastore.exception;


public class ApplicationException extends BaseException {

    /**
     *
     */
    private static final long serialVersionUID = -741215074424755266L;

    public ApplicationException(String code, String message) {
        super(code, message);
    }

    public ApplicationException(StatusResponse status) {
        super(status);
    }

}
