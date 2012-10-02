package com.oisix.oisystemfr.ejb;

import com.oisix.oisystemfr.EventHandler;
import com.oisix.oisystemfr.TransactionEvent;
import com.oisix.oisystemfr.Debug;
import java.util.HashMap;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * The entry point of EJB tier.
 *
 * @author Hiroto Yamashita
 * @version $Revision: 1.0 $
 *
 * @ejb:bean name="ControllerEjb"
 *           display-name="Controller EJB"
 *           type="Stateful"
 *           jndi-name="ControllerEjb"
 *           view-type="both"
 *
 * @ejb:ejb-ref ejb-name="Hacchuu"
 *              view-type="local"
 * @ejb:ejb-ref ejb-name="Hacchuukubun"
 *              view-type="local"
 * @ejb:ejb-ref ejb-name="Location"
 *              view-type="local"
 * @ejb:ejb-ref ejb-name="Nouhinsaki"
 *              view-type="local"
 * @ejb:ejb-ref ejb-name="Nyuukayoteimeisai"
 *              view-type="local"
 * @ejb:ejb-ref ejb-name="Nyuuko"
 *              view-type="local"
 * @ejb:ejb-ref ejb-name="Nyuukokubun"
 *              view-type="local"
 * @ejb:ejb-ref ejb-name="Office"
 *              view-type="local"
 * @ejb:ejb-ref ejb-name="Ondotai"
 *              view-type="local"
 * @ejb:ejb-ref ejb-name="Shiiresaki"
 *              view-type="local"
 * @ejb:ejb-ref ejb-name="Shouhin"
 *              view-type="local"
 * @ejb:ejb-ref ejb-name="Shukkayotei"
 *              view-type="local"
 * @ejb:ejb-ref ejb-name="Shukkayoteimeisai"
 *              view-type="local"
 * @ejb:ejb-ref ejb-name="Shukkayoteishubetsu"
 *              view-type="local"
 * @ejb:ejb-ref ejb-name="Shukko"
 *              view-type="local"
 * @ejb:ejb-ref ejb-name="Shukkokubun"
 *              view-type="local"
 * @ejb:ejb-ref ejb-name="Souko"
 *              view-type="local"
 * @ejb:ejb-ref ejb-name="Tani"
 *              view-type="local"
 * @ejb:ejb-ref ejb-name="Touchaku"
 *              view-type="local"
 * @ejb:ejb-ref ejb-name="User"
 *              view-type="local"
 * @ejb:ejb-ref ejb-name="Zaiko"
 *              view-type="local"
 * @ejb:ejb-ref ejb-name="Zaikomeisai"
 *              view-type="local"
 *
 **/
public class ControllerEjbBean implements SessionBean {

    private HashMap attr = new HashMap();
    private SessionContext context;

    /**
     * @ejb:interface-method view-type="both"
     **/
    public EventHandler handleEvent(TransactionEvent event) {
        Debug.println("now handling event", this);
        event.handleEvent(attr);
        if (event.getRollbackOnly()) {
            context.setRollbackOnly();
        }
        return event;
    }

    /**
     * @ejb:interface-method view-type="both"
     **/
    public boolean isAlive() {
        return true;
    }

    /**
     * @ejb:create-method view-type="both"
     **/
    public void ejbCreate() {}
    /**
     * @ejb:create-method view-type="both"
     **/
    public void ejbCreate(String dataSourceName) {
        ServiceLocator.setDataSourceName(dataSourceName);
    }
    public void setSessionContext(SessionContext ctx) {
        context = ctx;
    }
    public void ejbRemove() {}
    public void ejbActivate() {}
    public void ejbPassivate() {}
    public void ejbLoad() {}
    public void ejbStore() {}
}
