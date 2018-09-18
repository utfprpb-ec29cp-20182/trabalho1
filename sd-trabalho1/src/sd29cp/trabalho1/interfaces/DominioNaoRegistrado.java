package sd29cp.trabalho1.interfaces;


public class DominioNaoRegistrado extends Exception {

    @Override
    public String getMessage() {
        return "Erro: Domínio não registrado";
    }

    public DominioNaoRegistrado() {
        super();
    }
    
    public DominioNaoRegistrado(String msg) {
        super(msg);
    }
    
    
    
}
