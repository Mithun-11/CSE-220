
import java.util.*;



enum Status{NOT_STARTED,DEPARTMENT_CONFIRMED,OFFICE_ORDER_ISSUED,TESTIMONIAL_ISSUED,COMPLETED}


interface Mediator{
    void submitDepartmentConfirmation(DepartmentOffice sender, int studentID);
    void requestOfficeOrder(ExamControlOffice sender,int studentID);
    void requestTestimonial(DSW sender,int studentID);
    void requestCertificateTrans(ExamControlOffice sender,int studentID);
}

abstract class Office{
    protected Mediator mediator;

    Office(Mediator mediator){
        this.mediator=mediator;
    }

}

class DepartmentOffice extends Office{
    public DepartmentOffice(Mediator mediator){
        super(mediator);
    }

    void confirmRequirements(int studentID){
        mediator.submitDepartmentConfirmation(this, studentID);
    }
}

class ExamControlOffice extends Office{
    public ExamControlOffice(Mediator mediator){
        super(mediator);
    }

    void requestOfficeOrder(int studentID){
        mediator.requestOfficeOrder(this, studentID);
    }

    void requestCertificateTrans(int studentID){
        mediator.requestCertificateTrans(this, studentID);
    }
}

class DSW extends Office{

    public DSW(Mediator mediator){
        super(mediator);
    }

    void requestTestimonial(int studentID){
        mediator.requestTestimonial(this, studentID);
    }

}


class Student{
    private final int studentID;
    private final String name;

    private List<String> notifications= new ArrayList<>();

    public Student(int studentID,String name){
        this.studentID=studentID;
        this.name=name;
    }

    public int getID(){return studentID;}

    void notify(String message){
        notifications.add(message);
        System.out.println(name+" notified: "+ message);
    }

    void showNotifications(){
        System.out.println("\nNotifications recived by "+ name +":");
        for(String s: notifications){
            System.out.println(s);
        }
    } 


}


class Coordinator implements Mediator{

        private DepartmentOffice departmentOffice;
        private ExamControlOffice examControlOffice;
        private DSW dsw;
        private Map<Integer,Student> students= new HashMap<>();
        private Map<Integer,Status> statuses= new HashMap<>();

        void registerDepartment(DepartmentOffice departmentOffice){
            this.departmentOffice=departmentOffice;
        }

        void registerExamControl(ExamControlOffice examControlOffice){
            this.examControlOffice=examControlOffice;
        }

        void registerDSW(DSW dsw){
            this.dsw=dsw;
        }

        void registerStudent(Student student){
            students.put(student.getID(), student);
            statuses.put(student.getID(),Status.NOT_STARTED);
        }

        boolean studentExists(int studentID){
            if(!students.containsKey(studentID)) return false;

            return true;
        }

        private void rejectRequest(String s){
            System.out.println("Rejected: "+s);
        }


        @Override
        public void submitDepartmentConfirmation(DepartmentOffice sender, int studentID) {
           if(sender!= departmentOffice){
            rejectRequest("Unregistered Department Office");
            return;
           }
           if(!studentExists(studentID)) return;

           if(statuses.get(studentID)!=Status.NOT_STARTED){
            rejectRequest("Deparment Confirm not allowed");
            return;
           }

           statuses.put(studentID,Status.DEPARTMENT_CONFIRMED);
           students.get(studentID).notify("Department Confirmed");
        }

        @Override
        public void requestOfficeOrder(ExamControlOffice sender, int studentID) {
            if(sender!=examControlOffice) {
                rejectRequest("Unregistered Exam Control");
                return;
            }
            if(!studentExists(studentID)) return;

            if(statuses.get(studentID)!=Status.DEPARTMENT_CONFIRMED){
                rejectRequest("Department not confirmed");
                return;
            }

            statuses.put(studentID, Status.OFFICE_ORDER_ISSUED);

            students.get(studentID).notify("Final resutl published");

            
            
        }

        @Override
        public void requestTestimonial(DSW sender, int studentID) {
            if(!studentExists(studentID)) return;

            if(sender!=dsw) {
                rejectRequest("Unregistered DSW");
                return;
            }

            if(statuses.get(studentID)!=Status.OFFICE_ORDER_ISSUED){
                rejectRequest("Final result not published");
                return;
            }

            statuses.put(studentID,Status.TESTIMONIAL_ISSUED);
            students.get(studentID).notify("Testimonial Issued");
            
        }

        @Override
        public void requestCertificateTrans(ExamControlOffice sender, int studentID) {
            if(!studentExists(studentID)) return;

            if(sender!= examControlOffice){
                rejectRequest("Unregistered Control Office");
                return;
            }

            if(statuses.get(studentID)!=Status.TESTIMONIAL_ISSUED){
                rejectRequest("Steps Missing");
                return;
            }

            statuses.put(studentID,Status.COMPLETED);
            students.get(studentID).notify("Certificate and transcript issued");
            
        }


        public void displayStatus(int studentID){
            if(!studentExists(studentID)) return;

            System.out.println("Status of "+ studentID+": "+statuses.get(studentID));
        }
        
    }






public class MediatorPattern {
    public static void main(String[] args) {

        Coordinator coordinator= new Coordinator();

        DepartmentOffice departmentOffice= new DepartmentOffice(coordinator);
        ExamControlOffice examControlOffice = new ExamControlOffice(coordinator);
        DSW dsw = new DSW(coordinator);

        Student student= new Student(1, "Harry");

        coordinator.registerDepartment(departmentOffice);
        coordinator.registerExamControl(examControlOffice);
        coordinator.registerDSW(dsw);
        coordinator.registerStudent(student);

        examControlOffice.requestOfficeOrder(1);
         departmentOffice.confirmRequirements(1);

         examControlOffice.requestCertificateTrans(1);
         examControlOffice.requestOfficeOrder(1);

         dsw.requestTestimonial(1);

         examControlOffice.requestCertificateTrans(1);
         coordinator.displayStatus(1);
         student.showNotifications();
        
        
    }
}
