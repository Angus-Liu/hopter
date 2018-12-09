package org.hopter.plugin.security.tag;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.tags.PermissionTag;

/**
 * 判断当前用户是否拥有其中某一种权限
 *
 * @author Angus
 * @date 2018/12/9
 */
public class HasAnyPermissionsTag extends PermissionTag {

    /**
     * 逗号分隔，表示“与”的关系
     */
    private static final String PERMISSIONS_NAMES_DELIMITER = ",";

    @Override
    protected boolean showTagBody(String permissionNames) {
        boolean hasAnyPermission = false;
        Subject subject = getSubject();
        if (subject != null) {
            for (String permissionName : permissionNames.split(PERMISSIONS_NAMES_DELIMITER)) {
                if (subject.isPermitted(permissionName)) {
                    hasAnyPermission = true;
                    break;
                }
            }
        }
        return hasAnyPermission;
    }
}
