/*
 * @Author: gongxiaofei
 * @Date: 2023-04-11 09:41:41
 * @LastEditors: gongxiaofei
 * @LastEditTime: 2023-04-11 09:41:43
 * @Description:
 */
import dayjs from "dayjs";

const dateTimeUtils: any = {
  //返回当前时间
  now: () => {
    return dayjs().format("YYYY-MM-DD HH:mm:ss");
  },
  //返回今天
  today: () => {
    return dayjs().format("YYYY-MM-DD");
  },
};

export default dateTimeUtils;
