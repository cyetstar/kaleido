/*
 * @Author: gongxiaofei
 * @Date: 2022-08-09 10:04:53
 * @LastEditors: gongxiaofei
 * @LastEditTime: 2022-10-10 10:54:30
 * @Description: 
 */
import { defineOptionsConfig } from './utils'

export default defineOptionsConfig({
  AppType: [
    {
      name: '单模块应用',
      value: '0'
    },
    {
      name: '多模块应用',
      value: '1'
    }
  ],
  AppState: [
    {
      name: '上架',
      value: '0'
    },
    {
      name: '下架',
      value: '1'
    }
  ],
  AuditState: [
    {
      name: '未审批',
      value: '0'
    },
    {
      name: '审批通过',
      value: '1'
    },
    {
      name: '审批驳回',
      value: '2'
    },
  ],
  AppRole: [
    {
      name: '民警',
      value: '0'
    },
    {
      name: '协管民警',
      value: '1'
    },
    {
      name: '辅助民警',
      value: '2'
    }
  ],
  AppOpenType: [
    {
      name: '不开放',
      value: '0'
    },
    {
      name: '完全开放',
      value: '1'
    },
    {
      name: '受限开放',
      value: '2'
    }
  ],
  AppField: [
    {
      name: '系统应用',
      value: '0'
    },
    {
      name: '检查管理',
      value: '2'
    },
    {
      name: '其他',
      value: '3'
    }
  ],
  RegisterType: [
    {
      name: '系统预设',
      value: '0'
    },
    {
      name: '业务部门注册',
      value: '1'
    },
    {
      name: '外链应用',
      value: '2'
    }
  ],
  ModuleType: [
    {
      name: '主表单类',
      value: '2'
    },
    {
      name: '子表单类',
      value: '3'
    },
    {
      name: '指令类',
      value: '1'
    }
  ],
  // 操作类型
  OperationType: [
    {
      name: '新增',
      value: '1'
    },
    {
      name: '修改',
      value: '2'
    },
    {
      name: '启用',
      value: '3'
    },
    {
      name: '禁用',
      value: '4'
    },
    {
      name: '删除',
      value: '5'
    }
  ],
  // 审核状态
  AuditStatus: [
    {
      name: '待审核',
      value: '0'
    },
    {
      name: '已通过',
      value: '1'
    },
    {
      name: '已驳回',
      value: '2'
    }
  ],
  // 要素
  Element: [
    {
      name: '人',
      value: '1'
    },
    {
      name: '地',
      value: '2'
    },
    {
      name: '事',
      value: '3'
    },
    {
      name: '物',
      value: '4'
    },
    {
      name: '组织',
      value: '5'
    },
  ],
  SearchType: [
    {
      name: '等于',
      value: 'EQ'
    },
    {
      name: '模糊右(**%)',
      value: 'LIKE_RIGHT'
    },
    {
      name: '模糊左(%**)',
      value: 'LIKE_LEFT'
    },
    {
      name: '模糊(%**%)',
      value: 'LIKE'
    },
    {
      name: '大于等于',
      value: 'GE'
    },
    {
      name: '大于',
      value: 'GT'
    },
    {
      name: '小于等于',
      value: 'LE'
    },
    {
      name: '小于',
      value: 'LT'
    },
    {
      name: '正序',
      value: 'ASC'
    },
    {
      name: '倒序',
      value: 'DESC'
    },
  ]
})