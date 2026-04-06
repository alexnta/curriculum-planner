package com.alexnta.curriculumplanner.dal;

import com.alexnta.curriculumplanner.model.PrerequisiteGroup;
import com.alexnta.curriculumplanner.model.PrerequisiteItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PrerequisiteDAO {

    private final DBContext dbContext = new DBContext();

    public List<PrerequisiteGroup> findGroupsBySubjectId(int subjectId) {
        String sql = """
                SELECT g.prerequisite_group_id,
                       g.subject_id,
                       g.group_name,
                       g.min_required,
                       i.prerequisite_group_item_id,
                       i.prerequisite_subject_id
                FROM dbo.prerequisite_groups g
                LEFT JOIN dbo.prerequisite_group_items i
                    ON i.prerequisite_group_id = g.prerequisite_group_id
                WHERE g.subject_id = ?
                ORDER BY g.prerequisite_group_id, i.prerequisite_group_item_id
                """;

        Map<Integer, PrerequisiteGroup> groups = new LinkedHashMap<>();
        try (Connection connection = dbContext.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, subjectId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int groupId = resultSet.getInt("prerequisite_group_id");
                    PrerequisiteGroup group = groups.get(groupId);
                    if (group == null) {
                        group = new PrerequisiteGroup();
                        group.setPrerequisiteGroupId(groupId);
                        group.setSubjectId(resultSet.getInt("subject_id"));
                        group.setGroupName(resultSet.getString("group_name"));
                        group.setMinRequired(resultSet.getInt("min_required"));
                        groups.put(groupId, group);
                    }

                    int itemId = resultSet.getInt("prerequisite_group_item_id");
                    if (!resultSet.wasNull()) {
                        PrerequisiteItem item = new PrerequisiteItem();
                        item.setPrerequisiteGroupItemId(itemId);
                        item.setPrerequisiteGroupId(groupId);
                        item.setPrerequisiteSubjectId(resultSet.getInt("prerequisite_subject_id"));
                        group.getItems().add(item);
                    }
                }
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Failed to load prerequisite groups.", ex);
        }
        return new ArrayList<>(groups.values());
    }
}
