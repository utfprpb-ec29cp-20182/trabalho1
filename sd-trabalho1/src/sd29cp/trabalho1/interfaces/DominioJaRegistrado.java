package sd29cp.trabalho1.interfaces;


public class DominioJaRegistrado extends Exception {

    @Override
    public String getMessage() {
        return "Erro: domínio já está registrado";
    }

    public DominioJaRegistrado() {
        super();
    }

    public DominioJaRegistrado(String msg) {
        super(msg);
    }
}
