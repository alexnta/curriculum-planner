package com.alexnta.curriculumplanner.model;

public class PrerequisiteItem {

    private int prerequisiteGroupItemId;
    private int prerequisiteGroupId;
    private int prerequisiteSubjectId;

    public PrerequisiteItem() {
    }

    public PrerequisiteItem(int prerequisiteGroupItemId, int prerequisiteGroupId, int prerequisiteSubjectId) {
        this.prerequisiteGroupItemId = prerequisiteGroupItemId;
        this.prerequisiteGroupId = prerequisiteGroupId;
        this.prerequisiteSubjectId = prerequisiteSubjectId;
    }

    public int getPrerequisiteGroupItemId() {
        return prerequisiteGroupItemId;
    }

    public void setPrerequisiteGroupItemId(int prerequisiteGroupItemId) {
        this.prerequisiteGroupItemId = prerequisiteGroupItemId;
    }

    public int getPrerequisiteGroupId() {
        return prerequisiteGroupId;
    }

    public void setPrerequisiteGroupId(int prerequisiteGroupId) {
        this.prerequisiteGroupId = prerequisiteGroupId;
    }

    public int getPrerequisiteSubjectId() {
        return prerequisiteSubjectId;
    }

    public void setPrerequisiteSubjectId(int prerequisiteSubjectId) {
        this.prerequisiteSubjectId = prerequisiteSubjectId;
    }
}
