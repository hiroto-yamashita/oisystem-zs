package com.oisix.oisystemzs.taglib;

import com.oisix.oisystemfr.Debug;
import com.oisix.oisystemfr.taglib.CollectionTagBase;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ShukkoinputsuccessTag extends CollectionTagBase {

    protected Collection findCollection() throws Exception {
        Debug.println("findCollection start", this);

        HttpServletRequest request =
          (HttpServletRequest)pageContext.getRequest();
        HttpSession session = request.getSession();
        Collection resultCol =
          (Collection)session.getAttribute("INPUTRESULTSHUKKOLIST");
        session.setAttribute("INPUTRESULTSHUKKOLIST", null);
        return resultCol;
    }
}
