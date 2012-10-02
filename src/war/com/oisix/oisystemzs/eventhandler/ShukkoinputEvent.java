package com.oisix.oisystemzs.eventhandler;

import com.oisix.oisystemfr.EventHandlerSupport;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public class ShukkoinputEvent extends EventHandlerSupport {

    private int lines = 10;

    public void init(HttpServletRequest request) {
        HashMap inputval = (HashMap)session.getAttribute("INPUTVALUE");
        for (int i = 0; i < lines; i++) {
            String shouhin_id = "";
            if (inputval != null) {
                String[] shouhin_ids =
                  (String[])inputval.get("#shouhin_id" + i);
                if (shouhin_ids != null) {
                    shouhin_id = shouhin_ids[0];
                }
            }
            if (shouhin_id == null || shouhin_id.equals("")) {
                session.setAttribute("shouhinmei" + i, "");
                session.setAttribute("kikaku" + i, "");
            }
        }
    }

    public void handleEvent(HashMap attr) {
    }
}
