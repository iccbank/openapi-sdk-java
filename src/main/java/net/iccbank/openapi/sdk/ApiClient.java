package net.iccbank.openapi.sdk;

import java.math.BigDecimal;
import java.util.List;

import net.iccbank.openapi.sdk.model.ApiAddress;
import net.iccbank.openapi.sdk.model.ApiAgencyWithdrawData;
import net.iccbank.openapi.sdk.model.ApiAgencyWithdrawQueryData;
import net.iccbank.openapi.sdk.model.ApiCurrencyData;
import net.iccbank.openapi.sdk.model.ApiMchBalance;
import net.iccbank.openapi.sdk.model.ApiResponse;

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
	ApiResponse<List<ApiCurrencyData>> currencyAddToken(String linkType, String contractAddress);
	
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
}
