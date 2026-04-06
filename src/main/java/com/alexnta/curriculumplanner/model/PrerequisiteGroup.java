package com.alexnta.curriculumplanner.model;

import java.util.ArrayList;
import java.util.List;

public class PrerequisiteGroup {

    private int prerequisiteGroupId;
    private int subjectId;
    private String groupName;
    private int minRequired;
    private List<PrerequisiteItem> items = new ArrayList<>();

    public PrerequisiteGroup() {
    }

    public PrerequisiteGroup(int prerequisiteGroupId, int subjectId, String groupName, int minRequired) {
        this.prerequisiteGroupId = prerequisiteGroupId;
        this.subjectId = subjectId;
        this.groupName = groupName;
        this.minRequired = minRequired;
    }

    public int getPrerequisiteGroupId() {
        return prerequisiteGroupId;
    }

    public void setPrerequisiteGroupId(int prerequisiteGroupId) {
        this.prerequisiteGroupId = prerequisiteGroupId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getMinRequired() {
        return minRequired;
    }

    public void setMinRequired(int minRequired) {
        this.minRequired = minRequired;
    }

    public List<PrerequisiteItem> getItems() {
        return items;
    }

    public void setItems(List<PrerequisiteItem> items) {
        this.items = items;
    }
}
