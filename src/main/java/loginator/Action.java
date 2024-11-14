package loginator;

public class Action {
    private int actionId;
    private int studentId;
    private String actionTypeId;
    private String actDateTime;
    
    public int getActionId() {
        return actionId;
    }
    public void setActionId(int actionId) {
        this.actionId = actionId;
    }
    public int getStudentId() {
        return studentId;
    }
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
    public String getActionTypeId() {
        return actionTypeId;
    }
    public void setActionTypeId(String actionTypeId) {
        this.actionTypeId = actionTypeId;
    }
    public String getActDateTime() {
        return actDateTime;
    }
    public void setActDateTime(String actDateTime) {
        this.actDateTime = actDateTime;
    }
    public Action(int actionId, int studentId, String actionTypeId, String actDateTime) {
        this.actionId = actionId;
        this.studentId = studentId;
        this.actionTypeId = actionTypeId;
        this.actDateTime = actDateTime;
    }

    

}
