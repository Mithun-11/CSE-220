public class Prac {

    public static void main(String[] args) {
        
    }
}

/**
 * Prac
 */
interface State{
void updateReason(String reason);
void approve();
void reject();
void cancel();
void itemDelivered();
void inspect(boolean eligible);
void refundSuccessful();
void refundFailed()    ;
}

class Request{
    State state;
    String reason;
    public Request(String reason) {
        this.reason = reason;
        state=new Rquested(this);
    }
    public void updateReason(String reason) {
        state.updateReason(reason);
    }
    public void approve() {
        state.approve();
    }
    public void reject() {
        state.reject();
    }
    public void cancel() {
        state.cancel();
    }
    public void itemDelivered() {
        state.itemDelivered();
    }
    public void inspect(boolean eligible) {
        state.inspect(eligible);
    }
    public void refundSuccessful() {
        state.refundSuccessful();
    }
    public void refundFailed() {
        state.refundFailed();
    }
    
    
}
abstract class Base implements  State{
    Request request;

    public Base(Request request) {
        this.request = request;
    }

    public String getStateName() {
    return this.getClass().getSimpleName();
}

    @Override
    public void approve() {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void cancel() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void inspect(boolean eligible) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void itemDelivered() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void refundFailed() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void refundSuccessful() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void reject() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void updateReason(String reason) {
        // TODO Auto-generated method stub
        
    }

    void invalid(String s){
        System.out.println("This opearation is not usable inside");
    }
    
    
    

}