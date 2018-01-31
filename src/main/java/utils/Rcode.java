package utils;

/**
 * @author Acel
 * @since 2017/10/27
 */
public class Rcode {
    /**
     * 平台工程前缀
     */
    public static final String PRE_CODE = "eea";


    public static final String[] SUCCESS = {"e1000","success"};//no
    public static final String[] FAIL = {"e1001","fail"};//no
    public static final String[] PARAMS_ERROR = {"e1002","param.error"};// 参数错误
    public static final String[] SYSTEM_ERROR ={"e1003","system.error"};// 系统错误
    public static final String[] DRAW_SUCCESS ={"e1000","draw.success"};// 抽奖成功
    public static final String[] DRAW_FAILURE ={"e1001","draw.failure"};// 抽奖失败
    public static final String[] RECEIVE_SUCCESS ={"e1000","receive.success"};// 领取成功
    public static final String[] RECEIVE_FAILURE ={"e1001","receive.failure"};// 领取失败
    public static final String[] REPLY_SUCCESS ={"e1000","reply.success"};// 回复成功
    public static final String[] REPLY_FAILURE ={"e1001","reply.failure"};// 回复失败
    public static final String[] SUBMIT_SUCCESS ={"e1000","submit.success"};// 提交成功
    public static final String[] SUBMIT_FAILURE ={"e1001","submit.failure"};// 提交失败
    public static final String[] QUERY_SUCCESS ={"e1000","query.success"};// 查询成功
    public static final String[] QUERY_FAILURE ={"e1001","query.failure"};// 查询失败
    public static final String[] DELETE_SUCCESS ={"e1000","delete.success"};// 删除成功
    public static final String[] DELETE_FAILURE ={"e1001","delete.failure"};// 删除失败
    public static final String[] UPDATE_SUCCESS ={"e1000","update.success"};// 更新成功
    public static final String[] UPDATE_FAILURE ={"e1001","update.failure"};// 更新失败
    public static final String[] SIGIN_SUCCESS ={"e1000","sigin.success"};
    public static final String[] SIGIN1_SUCCESS ={"e1000","sigin1.success"};
    public static final String[] SIGIN3_SUCCESS ={"e1000","sigin3.success"};
    public static final String[] SIGIN7_SUCCESS ={"e1000","sigin7.success"};
    public static final String[] SIGIN15_SUCCESS ={"e1000","sigin15.success"};
    public static final String[] LOGIN_SUCCESS ={"e1000","login.success"};// 登录成功
    public static final String[] LOGIN_FAILURE ={"e1001","login.failure"};// 登录失败
    public static final String[] SAVE_FAILURE ={"e1000","save.failure"};// 保存失败
    public static final String[] SAVE_SUCCESS ={"e1001","save.success"};// 保存成功
    public static final String[] INVALID_TOKEN ={PRE_CODE+"501","invalid.token"};// 无效token
    public static final String[] INVALID_SIGN ={PRE_CODE+"502","invalid.sign"};// 签名错误
    public static final String[] INVALID_USER ={PRE_CODE+"503","invalid.user"};// 非法用户
    public static final String[] GOLD_LESS ={PRE_CODE+"504","gold.less"};// 积分不足
    public static final String[] POINT_LESS ={PRE_CODE+"505","point.less"};// 平台点不足
    public static final String[] FREE_POINT_LESS ={PRE_CODE+"506","free.point.less"};// 免费平台点不足
    public static final String[] TOO_MANY_TIMES ={PRE_CODE+"507","too.many.times"};// 请求过于频繁
    public static final String[] UNFINISHED ={PRE_CODE+"508","unfinished"};// 任务未完成
    public static final String[] HAS_GOT ={PRE_CODE+"509","has.got"};// 奖励已经领取
    public static final String[] NOT_SERIAL ={PRE_CODE+"510","not.serial"};// 序列号不足
    public static final String[] NOT_GIFT ={PRE_CODE+"511","not.gift"};// 礼包已下架
    public static final String[] VERIFY_CODE_ERROR ={PRE_CODE+"512","verify.code.error"};// 验证码错误
    public static final String[] VERIFY_CODE_EXPIRED ={PRE_CODE+"513","verify.code.expired"};// 验证码已过期
    public static final String[] GET_GIFT_PHONE_UNBIND ={PRE_CODE+"514","get.gift.phone.unbind"};// 领取礼包手机未绑定
    public static final String[] GET_GIFT_NEED_PLAY_GAME ={PRE_CODE+"515","get.gift.need.play.game"};// 领取礼包须先玩过该游戏
    public static final String[] GET_GIFT_SAME_PHONE_HAS_GOT ={PRE_CODE+"516","get.gift.same.phone.has.got"};// 相同的手机已经领取过该礼包
    public static final String[] NOT_BIND_PHONE = {PRE_CODE+"517","not.bind.phone"};//no
    public static final String[] EXIST = {PRE_CODE+"518","exist"};//no
    public static final String[] NOT_EXIST = {PRE_CODE+"519","not.exist"};//no
    public static final String[] NON_VIP = {PRE_CODE+"520","not.vip"};//no
    public static final String[] NO_MEMBER = {PRE_CODE+"521","no.member"};//no
    public static final String[] NOT_GAMEROLE = {PRE_CODE+"522","not.game.role"};//no
    public static final String[] STORE_UNFINISHED = {PRE_CODE+"523","store.unfinished"};//no ,储值条件不满足
    public static final String[] LOGIN_UNFINISHED = {PRE_CODE+"524","login.unfinished"};//no ,登陆条件不满足
    public static final String[] SIGN_ERROR ={PRE_CODE+"525","sign.error"};//no , sign错误
    public static final String[] CALL_SERVICE_ERROR = {PRE_CODE+"526","call.service.error"};//no ,调用服务发生错误
    public static final String[] ROLE_UNFINISHED = {PRE_CODE+"527","role.unfinished"};//no ,角色条件不满足
    public static final String[] ACCOUNT_NOT_CONFORM = {PRE_CODE+"528","account.not.conform"};//no ,账号不满足
    public static final String[] GAME_UNFINISHED = {PRE_CODE+"529","game.unfinished"};//no ,玩过的游戏条件不满足
    public static final String[] ILLEGAL_INPUT = {PRE_CODE+"530","illegal.input"};//no ,输入不合法
    public static final String[] NOT_SUPPORT_EMOJI = {PRE_CODE+"531","not.support.emoji"};//no,不支持emoji表情




}
