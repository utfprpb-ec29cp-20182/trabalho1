/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sd29cp.trabalho1.proxy;

import sd29cp.trabalho1.interfaces.ISMTP;
import sd29cp.trabalho1.interfaces.msgEmail;

/**
 *
 * @author favarim
 */
public class SMTPProxy implements ISMTP {
    private final String ipDNS;
    private final int portaDNS;

    public SMTPProxy(String ipDNS, int portaDNS) {
        this.ipDNS = ipDNS;
        this.portaDNS = portaDNS;
    }
    
    @Override
    public int enviar(msgEmail msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
