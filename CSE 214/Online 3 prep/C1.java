enum PATH{NORMAL,CRITICAL}
enum RAD{OK,NOT_OK}

interface Mediator{
    void onPathTest();
    void onRad();
    void onBoth();
}

abstract class Component {
    protected Mediator mediator;

    public Component(Mediator mediator) {
        this.mediator = mediator;
    }
}

class Doctor extends Component{

    public Doctor(Mediator mediator) {
        super(mediator);
    }
    
    void reqPath()
    {
        mediator.onPathTest();
    }

    void reqRad(){
        mediator.onRad();
    }

    void reqBoth(){
        mediator.onBoth();
    }

    void notify(String res){
        System.out.println("Result: "+res);
    }

}

class PathologyLab extends Component{

    public PathologyLab(Mediator mediator) {
        super(mediator);
    }
    
    void perform(){
        System.out.println("Performing Pathology test");
    }

    PATH report(){
        return PATH.CRITICAL;
    }


}

class RadioLab extends  Component{

    public RadioLab(Mediator mediator) {
        super(mediator);
    }
    
    void perform(){
        System.out.println("Performing Radiology test");
    }

    RAD report()
    {
        return RAD.NOT_OK;
    }


}



class EmCenter implements Mediator{

    Doctor doctor;
    PathologyLab pathologyLab;
    RadioLab radioLab;
    

    public EmCenter(Doctor doctor, PathologyLab pathologyLab, RadioLab radioLab) {
        this.doctor = doctor;
        this.pathologyLab = pathologyLab;
        this.radioLab = radioLab;
    }


    @Override
    public void onBoth() {
        
        
    }

    @Override
    public void onPathTest() {
        pathologyLab.perform();
        String r= "Report is"+ pathologyLab.report();
        doctor.notify(r);
        
    }

    @Override
    public void onRad() {
       radioLab.perform();
       String r="Report is "+ radioLab.report();
       doctor.notify(r);
        
    }
    
}

public class C1 {
    public static void main(String[] args) {
        
    }
    
}
