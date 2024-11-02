/*
 * @Author: gongxiaofei
 * @Date: 2023-04-11 09:41:41
 * @LastEditors: gongxiaofei
 * @LastEditTime: 2023-04-11 09:41:43
 * @Description:
 */
const IdUtils: any = {
  /*省,直辖市代码表*/
  provinceAndCitys: {
    11: "北京",
    12: "天津",
    13: "河北",
    14: "山西",
    15: "内蒙古",
    21: "辽宁",
    22: "吉林",
    23: "黑龙江",
    31: "上海",
    32: "江苏",
    33: "浙江",
    34: "安徽",
    35: "福建",
    36: "江西",
    37: "山东",
    41: "河南",
    42: "湖北",
    43: "湖南",
    44: "广东",
    45: "广西",
    46: "海南",
    50: "重庆",
    51: "四川",
    52: "贵州",
    53: "云南",
    54: "西藏",
    61: "陕西",
    62: "甘肃",
    63: "青海",
    64: "宁夏",
    65: "新疆",
    71: "台湾",
    81: "香港",
    82: "澳门",
    91: "国外",
  },

  /*每位加权因子*/
  powers: [
    "7",
    "9",
    "10",
    "5",
    "8",
    "4",
    "2",
    "1",
    "6",
    "3",
    "7",
    "9",
    "10",
    "5",
    "8",
    "4",
    "2",
  ],

  /*第18位校检码*/
  parityBit: ["1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"],

  /*性别*/
  genders: { male: "男", female: "女" },

  /*校验地址码*/
  checkAddressCode: function (addressCode: string) {
    let check = /^[1-9]\d{5}$/.test(addressCode);
    if (!check) {
      return false;
    }
    if (IdUtils.provinceAndCitys[parseInt(addressCode.substring(0, 2))]) {
      return true;
    } else {
      return false;
    }
  },

  /*校验日期码*/
  checkBirthDayCode: function (birDayCode: string) {
    let check =
      /^[1-9]\d{3}((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))$/.test(
        birDayCode
      );
    if (!check) {
      return false;
    }
    const yyyy = parseInt(birDayCode.substring(0, 4), 10);
    const mm = parseInt(birDayCode.substring(4, 6), 10);
    const dd = parseInt(birDayCode.substring(6), 10);
    const xdata = new Date(yyyy, mm - 1, dd);
    if (xdata > new Date()) {
      return false; //生日不能大于当前日期
    } else if (
      xdata.getFullYear() === yyyy &&
      xdata.getMonth() === mm - 1 &&
      xdata.getDate() === dd
    ) {
      return true;
    } else {
      return false;
    }
  },

  /*计算校检码*/
  getParityBit: function (idCardNo: string) {
    let id17 = idCardNo.substring(0, 17);
    /*加权 */
    let power = 0;
    for (let i = 0; i < 17; i++) {
      power += parseInt(id17.charAt(i), 10) * parseInt(IdUtils.powers[i]);
    }
    /*取模*/
    let mod = power % 11;
    return IdUtils.parityBit[mod];
  },

  /*验证校检码*/
  checkParityBit: function (idCardNo: string) {
    let parityBit = idCardNo.charAt(17).toUpperCase();
    if (IdUtils.getParityBit(idCardNo) === parityBit) {
      return true;
    } else {
      return false;
    }
  },

  /*校验15位或18位的身份证号码*/
  checkIdCardNo: function (idCardNo: string) {
    //15位和18位身份证号码的基本校验
    const check = /^\d{15}|(\d{17}(\d|x|X))$/.test(idCardNo);
    if (!check) {
      return false;
    }
    //判断长度为15位或18位
    if (idCardNo.length === 15) {
      return IdUtils.check15IdCardNo(idCardNo);
    } else if (idCardNo.length === 18) {
      return IdUtils.check18IdCardNo(idCardNo);
    } else {
      return false;
    }
  },

  //校验15位的身份证号码
  check15IdCardNo: function (idCardNo: string) {
    //15位身份证号码的基本校验
    let check =
      /^[1-9]\d{7}((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))\d{3}$/.test(
        idCardNo
      );
    if (!check) {
      return false;
    }
    //校验地址码
    let addressCode = idCardNo.substring(0, 6);
    check = IdUtils.checkAddressCode(addressCode);
    if (!check) {
      return false;
    }
    let birDayCode = "19" + idCardNo.substring(6, 12);
    //校验日期码
    return IdUtils.checkBirthDayCode(birDayCode);
  },

  //校验18位的身份证号码
  check18IdCardNo: function (idCardNo: string) {
    //18位身份证号码的基本格式校验
    let check =
      /^[1-9]\d{5}[1-9]\d{3}((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))\d{3}(\d|x|X)$/.test(
        idCardNo
      );
    if (!check) {
      return false;
    }
    //校验地址码
    let addressCode = idCardNo.substring(0, 6);
    check = IdUtils.checkAddressCode(addressCode);
    if (!check) {
      return false;
    }
    //校验日期码
    let birDayCode = idCardNo.substring(6, 14);
    check = IdUtils.checkBirthDayCode(birDayCode);
    if (!check) {
      return false;
    }
    //验证校检码
    return IdUtils.checkParityBit(idCardNo);
  },

  formateDateCN: function (day: string) {
    let yyyy = day.substring(0, 4);
    let mm = day.substring(4, 6);
    let dd = day.substring(6);
    return yyyy + "-" + mm + "-" + dd;
  },

  //获取信息
  getIdCardInfo: function (idCardNo: string) {
    let idCardInfo = {
      gender: "", //性别
      birthday: "", // 出生日期(yyyy-mm-dd)
    };
    if (idCardNo.length === 15) {
      const aday = "19" + idCardNo.substring(6, 12);
      idCardInfo.birthday = IdUtils.formateDateCN(aday);
      if (parseInt(idCardNo.charAt(14)) % 2 === 0) {
        idCardInfo.gender = IdUtils.genders.female;
      } else {
        idCardInfo.gender = IdUtils.genders.male;
      }
    } else if (idCardNo.length === 18) {
      const aday = idCardNo.substring(6, 14);
      idCardInfo.birthday = IdUtils.formateDateCN(aday);
      if (parseInt(idCardNo.charAt(16)) % 2 === 0) {
        idCardInfo.gender = IdUtils.genders.female;
      } else {
        idCardInfo.gender = IdUtils.genders.male;
      }
    }
    return idCardInfo;
  },

  /*15位转18位*/
  convertId: function (idCardNo: string) {
    if (idCardNo.length === 15) {
      let id17 = idCardNo.substring(0, 6) + "19" + idCardNo.substring(6);
      let parityBit = IdUtils.getParityBit(id17);
      return id17 + parityBit;
    } else if (idCardNo.length === 18) {
      return idCardNo;
    } else {
      return null;
    }
  },

  getXbFromId: function (sfhm: string) {
    if (sfhm === null) {
      return null;
    }
    let sfhm18 = sfhm;
    if (sfhm.length !== 18) {
      sfhm18 = this.convertId(sfhm);
    }
    let cxb = +sfhm18.charAt(16);
    let ixb = cxb % 2;
    if (ixb === 0) {
      ixb = 2;
    }
    return String(ixb);
  },

  getCsrqFromId: function (sfhm: string) {
    if (sfhm === null) {
      return null;
    }
    let sfhm18 = sfhm;
    if (sfhm.length !== 18) {
      sfhm18 = this.convertId(sfhm);
    }
    return `${sfhm18.substr(6, 4)}-${sfhm18.substr(10, 2)}-${sfhm18.substr(
      12,
      2
    )}`;
  },

  getHksxFromId: function (sfhm: string) {
    if (sfhm === null) {
      return null;
    }
    let sfhm18 = sfhm;
    if (sfhm.length !== 18) {
      sfhm18 = this.convertId(sfhm);
    }
    return sfhm18.substr(0, 6);
  },
};

export default IdUtils;
