import store from '@/store'
import permission from "../store/modules/permission";

/**
 * @param {Array} value
 * @returns {Boolean}
 * @example see @/views/permission/directive.vue
 */
export default function checkPermission(value) {
  if (value && value instanceof Array && value.length > 0) {
    const roles = store.getters && store.getters.roles
    const permissionRoles = value

    const hasPermission = roles.some(role => {
      return permissionRoles.includes(role)
    })

    if (!hasPermission) {
      return false
    }
    return true
  } else {
    console.error(`need roles! Like v-permission="['admin','editor']"`)
    return false
  }
}

/**
 * 校验是否具有某权限
 * @param value
 * @returns {boolean}
 */
export  function hasPermission(value) {
  if (value) {
    const permissions = store.getters && store.getters.permissions;
    const hasPermission = permissions.some(permission => {
      return permission.authority.indexOf(value) >= 0;
    })

    if (!hasPermission) {
      return false
    }
    return true
  } else {
    return true
  }
}
