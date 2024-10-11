/*
 * @Author: gongxiaofei
 * @Date: 2022-10-20 09:31:08
 * @LastEditors: gongxiaofei
 * @LastEditTime: 2022-10-20 09:33:35
 * @Description: 
 */
import { usePermissionStore } from '@/store/modules/permission'
export default (app: any) => {
    //自定义组件
    app.directive('permissKey', (el: any, binding: any) => {
        const pStore = usePermissionStore()
        let allPermission = new Array(...pStore.permissionKeys)
        if(binding.value != "") {
            if(!allPermission.includes(binding.value)) {
                el.parentNode && el.parentNode.removeChild(el);
            }
        }
    })
}