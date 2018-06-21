package com.qiugaoyang.qgyblog.common.util;

public interface MesUtil {
	public static final String SUCCESS = "11001";
	public static final String IPERROR = "10001";
	public static final String LOGINERROR = "10002";// 用户密码错误
	public static final String REGISTERERROR = "10003";// 注册失败
	public static final String UPDPASSERROR = "10004";// 更改密码失败
	public static final String MAILERROR = "10005";//邮箱已存在
	public static final String PHONERROR = "10006";//手机号已存在
	public static final String MISSERROR = "10007"; //missing
	public static final String DATEERROR = "10008"; //传输数据错误
	public static final String INSERTERROR = "10009"; //插入失败
	public static final String SELECTERROR = "10012"; //查询失败
	public static final String TOKENERROR = "10010"; //Token 错误
	public static final String TIMEERROR = "10011"; //Token 过期
	public static final String STATEERROR = "10012"; //状态 错误
	public static final String UPDERROR = "10013"; //更新错误
	public static final String CODEERROR = "10014"; //验证码错误
	public static final String ELSEERROR = "11000"; //其他错误
	public static final String REDISERROR = "12000"; //Redis错误
	public static final String CARTSERROR = "已添加至购物车."; //已添加购物车
	public static final String STOCKERROR = "库存不足";//商品库存不足
	public static final String GOODSSTATERROR="商品已下架或未通过审核";//商品已下架或未通过审核
	public static final String ORDERNOTCOMPLETEDERROR = "订单未完成，无法评论";
	public static final String COMMENTEDORDERSERROR = "已评论过的订单";
	
	public static final int EXISTTIME = 30; //Redis存在时间 分
	public static final int MISSTIME = 1790; //Redis失效时间 秒
	public static final int CODEMISSTIME = 600; //Redis 短信失效时间 秒
	public static final Integer REIDS_SENDNUM = 10; //Redis 缓存存在内发送过短信上线
	public static final Integer REIDS_IPSENDNUM = 10; //Redis 缓存存在内发送过短信上线(IP控制)
	public static final Integer TIME_INTERVAL = 15;//下单有效期的时间间隔
	public static final int IPCHECK = 0;//IP验证(PC端)：（0：N 1：Y）
	
	public static final String REIDS_CATE = "HOME:CATELIST"; //分类
	public static final String REIDS_FIRST_CATE = "HOME:FIRST_CATELIST"; //分类第一层
	public static final String REIDS_PIC = "HOME:PICLIST"; //首页轮播图
	public static final String REIDS_HOME = "HOME:HOMELIST"; //首页推荐图
	public static final String REIDS_CATELIST = "HOME:CATE_LIST"; //首页分类推荐+cateID
	public static final String REIDS_ACTIVITY = "HOME:ACTIVITYLIST"; //首页活动商品
	public static final String RECOMMEND_RECOMMEND = "HOME:CATE_RECOMMEND"; //推荐cate
	
	

	/*String USER_CART = "USER_CART:"; //购物车
	String USER_CART_HOME = "USER_CART_HOME:"; //购物车简略信息
*/
	public static final String REIDS_PRODUCT_PIC = "PRODUCT_PIC:"; //商品 购买时更新
	
	
	public static final String REIDS_PRODUCT = "PRODUCT:"; //商品 购买时更新
	public static final String REIDS_PRODUCT_PRICE = "PRODUCT_PRICE:"; //商品价格 购买时更新
	
	
	
	
	public static final String NOTENAME = "NOTELIST"; //NOTE_INFO
	

	public static final String TOKENCOMMON = "USER_TOKEN:"; //TOKEN
	public static final String TOKENNMAE = "TOKEN"; //TOKEN
	public static final String USERID = "USERID"; //TOKEN
	public static final String IPNMAE = "IP"; //TOKEN
	public static final String TIMENMAE = "TIME"; //TOKEN
	
	public static final String ImgPath = "imgs"; //IMG Path
	public static final String ImgName = "goodsIMG"; //IMG Name
	
	public static final String OSS_HOME = "http://tongtai.oss-cn-shanghai.aliyuncs.com/"; 
	public static final String OSS_HOME_SFF = "home/shufflingFigure"; //OSS首页轮播图
	public static final String OSS_PRODUCT = "product"; //OSS产品目录
	public static final String OSS_PRODUCT_DESC = "product_desc"; //OSS产品目录
	public static final String OSS_SUPPLIER = "supplier"; //OSS产品目录
	public static final String OSS_QR = "qrcode"; //OSS二维码目录
	public static final String OSS_MESSAGEIMG = "messageimg";//OSS留言图片目录
//	String STATEDEL = "删除";
//	String STATEPENDING = "待审核";
//	String STATEUSE = "使用中";
	
//	String STATDOWN = "下架";
	public static final String STATEPASS = "审核通过";
	public static final String STATENOPASS = "审核未通过";
	
	//app_id 
	public static final String appId = "2017031306205218";
	//sell_id
	public static final String sellId = "2088621543103869";
	
	//上级返利份额
	public static final String referrerProfit = "referrerProfit";
	
	//下级返利份额
	public static final String underProfit = "underProfit";
	
	//自身返利份额
	public static final String userProfit = "userProfit";
	
	//平台返利份额
	public static final String selfProfit = "selfProfit";
	
	//同台账号名 TONGTAI_id
	public static final String TONGTAI = "TONGTAI_id";
	
	//首页广告位数名
	public static final String AdvertismentNum = "AdvertismentNum";
	
	//用户升级费用
	public static final String userLevel ="会员升级";
	
	//平台轮播图
	public static final String Platform = "Platform";
}


