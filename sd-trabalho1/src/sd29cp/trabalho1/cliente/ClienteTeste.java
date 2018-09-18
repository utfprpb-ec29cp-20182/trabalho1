/*
 * Classe cliente para teste do serviço de email
 */
package sd29cp.trabalho1.cliente;

import java.util.List;

import sd29cp.trabalho1.interfaces.*;
import sd29cp.trabalho1.proxy.*;

/**
 *
 * @author favarim
 */
public class ClienteTeste {

    private final int portaDNS;
    private final String ipDNS;
    private final IDNS dns;
    private String de;
    private String para;

    public ClienteTeste(String ipDNS, int portaDNS) {
        this.portaDNS = portaDNS;
        this.ipDNS = ipDNS;
        System.out.println("Cliente iniciado!!!");
        dns = new DNSProxy(ipDNS, portaDNS);
        executarTestes();
    }

    private void executarTestes() {
        // Deve-se inicializar dois servidores de email para os seguintes domínios
        // utfpr.edu.br 
        // gmail.com

        System.out.println("Teste 1: envio de email para o mesmo servidor smtp");
        testeEnvioEmail("professor@utfpr.edu.br", "aluno@utfpr.edu.br", "Teste 1", "Mensagem 1");

        System.out.println("**************************************************");
        System.out.println("Teste 2: envio de email para um servidor smtp diferente");
        testeEnvioEmail("professor@utfpr.edu.br", "aluno@gmail.com", "Teste 2", "Mensagem 2");

        System.out.println("**************************************************");
        System.out.println("Teste 3: servidor smtp de origem não existente");
        testeEnvioEmail("professor@hotmail.com", "aluno@utfpr.edu.br", "Teste 3", "Mensagem 3");

        System.out.println("**************************************************");
        System.out.println("Teste 4: servidor smtp de destino não existente");
        testeEnvioEmail("professor@utfpr.edu.br", "aluno@hotmail.com", "Teste 3", "Mensagem 4");

        System.out.println("**************************************************");
        System.out.println("Teste 5: leitura de email mantendo as mensagens no servidor");
        testeLeituraEmail("aluno@utfpr.edu.br", true);
        testeLeituraEmail("aluno@gmail.com", true);

        System.out.println("**************************************************");
        System.out.println("Teste 6: leitura de email NÃO mantendo as mensagens no servidor");
        testeLeituraEmail("aluno@utfpr.edu.br", false);
        testeLeituraEmail("aluno@gmail.com", false);

        System.out.println("**************************************************");
        System.out.println("Teste 7: leitura de email para ver se as mensagens forma excluídas");
        testeLeituraEmail("aluno@utfpr.edu.br", true);
        testeLeituraEmail("aluno@gmail.com", true);

    }

    private void testeEnvioEmail(String de, String para, String assunto, String mensagem) {

        try {
            System.out.println("Resolvendo nome do servidor smtp de origem!!!");
            String ipPortaSMTP = dns.lookup("smtp." + de.split("\\@")[1]);
            String ipSMTP = ipPortaSMTP.split(" ")[0];
            int portaSMTP = Integer.valueOf(ipPortaSMTP.split(" ")[1]);
            System.out.println("Servidor encontrado: smtp." + de.split("\\@")[1] + ":" + ipSMTP + ":" + portaSMTP);

            // 2) invocar metodo enviar do objeto smtp
            System.out.println("Enviando email para o servidor SMTP!!!");
            ISMTP smtp = new SMTPProxy(ipSMTP, portaSMTP);
            int resposta = smtp.enviar(new msgEmail(de, para, assunto, mensagem));
            if (resposta == 0) {
                System.out.println("Mensagem Enviada com Sucesso!!!");
            } else if (resposta == 1) {
                System.out.println("Erro: Mensagem não enviada!!! Servidor SMTP de destino não localizado.");
            }

        } catch (DominioNaoRegistrado e) {
            System.out.println("Erro: Mensagem não enviada!!! Servidor SMTP de origem não localizado.");
        }

    }

    private void testeLeituraEmail(String nome, boolean manterNoServidor) {
        List<msgEmail> listaEmails = null;

        // 1) localizar o servidor pop
        try {
            System.out.println("Resolvendo nome do servidor pop!!!");
            String ipPortaPOP = dns.lookup("pop." + nome.split("\\@")[1]);
            String ipPOP = ipPortaPOP.split(" ")[0];
            int portaPOP = Integer.valueOf(ipPortaPOP.split(" ")[1]);
            System.out.println("Servidor: pop." + nome.split("\\@")[1] + ":" + ipPOP + ":" + portaPOP);

            // 2) invocar metodo ler do objeto pop
            System.out.println("Lendo emails do servidor POP!!!");
            IPOP pop = new POPProxy(ipPOP, portaPOP);
            listaEmails = pop.ler(nome.split("\\@")[0], manterNoServidor);

            if (listaEmails.isEmpty()) {
                System.out.println("Nenhuma nova mensagem!!!");
            } else {
                System.out.println(listaEmails.size() + " novas mensagens!!!");
                int nrEmails = 0;
                for (msgEmail email : listaEmails) {
                    System.out.print("----- Email recebido " + ++nrEmails + "----\n");
                    System.out.print(" De: " + email.getDe() + "\n");
                    System.out.print(" Para: " + email.getPara() + "\n");
                    System.out.print(" Assunto: " + email.getAssunto() + "\n");
                    System.out.println(" Mensagem: " + email.getMensagem() + "\n");
                }
            }

        } catch (DominioNaoRegistrado e) {
            System.out.println("Servidor POP não localizado!!!");
        }

    }

    public static void main(String[] args) {
        ClienteTeste c = new ClienteTeste("127.0.0.1", 10053);
    }

}
