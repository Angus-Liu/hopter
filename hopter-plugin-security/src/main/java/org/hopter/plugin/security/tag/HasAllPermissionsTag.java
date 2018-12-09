package org.hopter.plugin.security.tag;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.tags.PermissionTag;

/**
 * 判断当前用户是否拥有其中所有的权限
 *
 * @author Angus
 * @date 2018/12/9
 */
public class HasAllPermissionsTag extends PermissionTag {

    /**
     * 逗号分隔，表示“与”的关系
     */
    private static final String PERMISSIONS_NAMES_DELIMITER = ",";

    @Override
    protected boolean showTagBody(String permissionNames) {
        boolean hasAllPermission = false;
        Subject subject = getSubject();
        if (subject != null) {
            if (subject.isPermittedAll(permissionNames.split(PERMISSIONS_NAMES_DELIMITER))) {
                hasAllPermission = true;
            }
        }
        return hasAllPermission;
    }
}
