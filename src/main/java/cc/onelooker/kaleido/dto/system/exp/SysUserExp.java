package cc.onelooker.kaleido.dto.system.exp;

import com.alibaba.excel.annotation.ExcelProperty;
import com.zjjcnt.common.core.annotation.Dict;
import com.zjjcnt.common.core.annotation.Mask;
import com.zjjcnt.common.core.enums.MaskType;
import lombok.Data;

/**
 * @Author xiadawei
 * @Date 2022-07-06 14:41:00
 * @Description TODO
 */
@Data
public class SysUserExp {

    @ExcelProperty("用户编号")
    private Long userId;

    @ExcelProperty("用户名")
    private String username;

    @Mask(type = MaskType.NAME)
    @ExcelProperty("姓名")
    private String name;

    @Mask(type = MaskType.MOBILE)
    @ExcelProperty("手机号")
    private String mobile;

    @Dict("sysDept")
    @ExcelProperty("部门名称")
    private Long deptId;

    @ExcelProperty("部门代码")
    private String deptCode;

}
