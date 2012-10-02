package com.oisix.oisystemzs.taglib;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.taglib.CollectionTagBase;
import com.oisix.oisystemfr.TransactionServlet;
import com.oisix.oisystemzs.eventhandler.DonyuukoinputEvent;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class NyuukoinputsuccessTag extends CollectionTagBase {

    protected Collection findCollection() throws Exception {
        HttpServletRequest request =
          (HttpServletRequest)pageContext.getRequest();
        DonyuukoinputEvent event = (DonyuukoinputEvent)
          request.getAttribute(TransactionServlet.RESULTKEY);
        return event.getResultNyuukoList();
    }
}
