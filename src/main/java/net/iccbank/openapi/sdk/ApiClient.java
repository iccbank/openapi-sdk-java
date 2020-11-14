package net.iccbank.openapi.sdk;

import java.math.BigDecimal;
import java.util.List;

import net.iccbank.openapi.sdk.model.*;

public interface ApiClient {
	
	/**
	 * 地址合法性校验
	 * @param currencyCode 币种代码
	 * @param address 地址
	 */
	ApiResponse<Object> addressCheck(String currencyCode, String address);
	
	/**
	 * 地址合法性校验
	 * @param currencyCode 币种代码
	 * @param address 地址
	 * @param labelAddress 标签地址
	 */
	ApiResponse<Object> addressCheck(String currencyCode, String address, String labelAddress);
	
	/**
	 * 代付地址注册
	 * 
	 * @param currencyCode 币种代码
	 * @param address 代付地址
	 * @param labelAddress 地址标签, EOS或XRP等地址使用（非必填）
	 */
	ApiResponse<Object> agentPayAddAddress(String currencyCode, String address, String labelAddress);
	
	/**
	 * 创建代收地址
	 * 
	 * @param currencyCode 币种代码
	 * @param count 地址数量(1-100)
	 * @param batchNumber 批次号
	 */
	ApiResponse<List<ApiAddress>> createAgencyRechargeAddress(String currencyCode, int count, String batchNumber);
	
	/**
	 * 代付
	 * 
	 * @param userBizId 商户订单号
	 * @param subject 订单描述（非必填）
	 * @param currencyCode 币种代码
	 * @param address 代付地址
	 * @param labelAddress 地址标签, EOS或XRP等地址使用（非必填）
	 * @param amount 金额，代付地址实际到账的金额，商户支付的手续费平台自动扣除并返回
	 * @param notifyUrl 通知地址（非必填） 为空则不通知
	 */
	ApiResponse<ApiAgencyWithdrawData> agencyWithdraw(String userBizId, String subject, String currencyCode, String address, String labelAddress, BigDecimal amount, String notifyUrl);

	/**
	 * 代付(可以指定矿工费)
	 * 
	 * @param userBizId 商户订单号
	 * @param subject 订单描述（非必填）
	 * @param currencyCode 币种代码
	 * @param address 代付地址
	 * @param labelAddress 地址标签, EOS或XRP等地址使用（非必填）
	 * @param amount 金额，代付地址实际到账的金额，商户支付的手续费平台自动扣除并返回
	 * @param minerFee 矿工费（商户指定矿工费）
	 * @param fee 手续费（商户指定手续费）
	 * @param notifyUrl 通知地址（非必填） 为空则不通知
	 */
	ApiResponse<ApiAgencyWithdrawData> agencyWithdrawWithMinerFee(String userBizId, String subject, String currencyCode, String address, String labelAddress, 
			BigDecimal amount, 
			BigDecimal minerFee, 
			BigDecimal fee,
			String notifyUrl);
	
	/**
	 * 代付订单查询
	 * 
	 * @param @param userBizId 商户订单号
	 */
	ApiResponse<ApiAgencyWithdrawQueryData> queryAgencyWithdrawOrder(String userBizId);

	/**
	 * @Author kevin
	 * @Description 获取账户余额列表
	 * @Date Created on 2020/7/8 15:25
	 */
	ApiResponse<ApiMchBalance> getBalances();

	/**
	 * @Description 币种搜索
	 * @Date Created on 2020/8/24 19:15
	 */
	ApiResponse<List<ApiCurrencyData>> currencySearch(int searchType, String keywords);
	
	/**
	 * @Description 添加代币
	 * @Date Created on 2020/8/24 19:15
	 */
	ApiResponse<ApiCurrencyData> currencyAddToken(String linkType, String contractAddress);
	
	/**
	 * @Description 代币手续费
	 * @Date Created on 2020/8/24 19:15
	 */
	ApiResponse<ApiCurrencyFeeData> getCurrencyFee(String currencyCode);

	/**
	 * @Description 查询主链币列表
	 * @Date Created on 2020/8/24 19:15
	 */
	ApiResponse<List<ApiCurrencyData>> queryCurrencyChainList();

	/**
	 * @Description 获取币种信息
	 * @Date Created on 2020/8/24 19:15
	 */
	ApiResponse<ApiCurrencyData> getCurrencyByCode(String currencyCode);
	
	/**
	 * @Author kevin
	 * @Description 查询账户（指定币种）余额列表
	 * @Date Created on 2020/7/8 11:41
	 * @param currencyCode 币种
	 */
	ApiResponse<ApiMchBalance> getBalancesForCurrencyCode(String currencyCode);

	/**
	 * @Author kevin
	 * @Description 查询账户余额
	 * @Date Created on 2020/7/8 11:41
	 * @param currencyCode 币种
	 * @param accountType 币种
	 */
	ApiResponse<ApiMchBalance.BalanceNode> getBalancesForCurrencyCodeAndAccountType(String currencyCode, Long accountType);

	/**
	 * @Author huailong.wang
	 * @Description 查询账户（指定币种）总资产
	 * @Date Created on 2020/10/26 11:41
	 * @param currencyCode 币种
	 */
	ApiResponse<ApiMchBalance.BalanceNode> getTotalBalancesForCurrencyCode(String currencyCode);

	/**
	 * @Author panYong
	 * @Description 查询旷工算力，minerAddress非必传
	 * @Date Created on 2020/11/14 13:09
	 * @param minerAddress 币种
	 */
	ApiResponse<ApiMinerPower> getMinerPower(String currencyCode,String minerAddress);

}
