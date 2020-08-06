# ICCBank OpenAPI Documents

## 1 - 接口列表
- 1.1 - [地址合法性校验](#地址合法性校验)
- 1.2 - [获取代收地址](#获取代收地址)
- 1.3 - [代收通知](#代收通知)
- 1.4 - [代付地址注册](#代付地址注册)
- 1.5 - [代付](#代付)
- 1.6 - [代付订单查询](#代付订单查询)
- 1.7 - [代付通知](#代付通知)
- 1.8 - [账户余额查询](#账户余额查询)

## 2 - 支持币种（以ICCBank官网为准）
|Code|Desc|
|:----    |:------- |
|BTC    |比特币    |
|BCH    |比特现金    |
|BSV    |比特币SV    |
|LTC    |莱特币    |
|ETH    |以太坊    |
|ETC    |以太经典    |
| USDT_OMNI   |  泰达币  |
|  USDT_ERC20  |  泰达币  |
| IONC   | 离子币   |
| FIL   |  文件币  |
|  XMR  | 门罗币   |
|  DASH  |  达世币  |
|  TRX  |  波场  |
|  XRP  |  瑞波  |

## 3 - 请求方式
- HTTPS + JSON

## 4 - SDK列表
- [Java](./README-JAVA.md)

## 5. 公共请求参数
| Header        | 必选   |  类型  | 说明 |
|:--------   |:-----  |:----  | |
| OPENAPI_APP_ID      | 是   |   string     | 值为appId |

| Body        | 必选   |  类型  | 说明 |
|:----    |:---|:----- |-----   |
|appId |是  |string |appId   |
|timestamp |是  |long | 时间戳    |
|nonce |是  |string |随机数   |
|signType |是  |string |签名类型，RSA   |
|sign |是  |string |签名，不参与签名   |

** 说明：** 
每个接口都需要添加公共请求参数，Body数据除sign之外其他都需要参与签名

## 6. 报文加解密和签名

### 6.1 签名规则
采用RSA算法签名，请求body里所有的数据都参与签名(sign除外), 签名的明文是queryString格式的字符串，key值按字母序排列，参数值为空不参与签名。

#### 签名前的原始数据
```
{
	"address": "0xC1b9276626e78447ce728Fd030145d8d21E3619E",
	"appId": "f678b3e698f649faa2177536356f83f7",
	"currencyCode": "IONC",
	"nonce": "6c37a207a07a4f6f93011b2ea59ee38b",
	"signType": "RSA",
	"timestamp": 1594892574165
}
```


#### 签名原始串（Key从a-z排列，json字符串中间无空格）
```
address=0xC1b9276626e78447ce728Fd030145d8d21E3619E&appId=f678b3e698f649faa2177536356f83f7&currencyCode=IONC&nonce=6c37a207a07a4f6f93011b2ea59ee38b&signType=RSA&timestamp=1594892574165
```

#### 签名值
```
c/Mo2GyQ0SO8x9AR/6GraWSuCZziXvavpGBtYU5hmEA+Y/s+mGhbhmSvr5AYWLW6ErcJ22+1kz3TFtmajxAawxIZ03tALraB4zkKxuGAO8VyCBoXo5aR4uR2xTMOdG0hOb5QVEI3+3L8oCocn5eyd68PnbQEbfwMfQuB6tZADxI=
```


### 6.2 加解密规则
采用DESede算法加解密数据，加密和解密过程相反。

#### 签名后原始数据
```
{
	"address": "0xC1b9276626e78447ce728Fd030145d8d21E3619E",
	"appId": "f678b3e698f649faa2177536356f83f7",
	"currencyCode": "IONC",
	"nonce": "6c37a207a07a4f6f93011b2ea59ee38b",
	"sign": "c/Mo2GyQ0SO8x9AR/6GraWSuCZziXvavpGBtYU5hmEA+Y/s+mGhbhmSvr5AYWLW6ErcJ22+1kz3TFtmajxAawxIZ03tALraB4zkKxuGAO8VyCBoXo5aR4uR2xTMOdG0hOb5QVEI3+3L8oCocn5eyd68PnbQEbfwMfQuB6tZADxI=",
	"signType": "RSA",
	"timestamp": 1594892574165
}
```

#### 数据密文
```
35rSBkNNrwk53Nrd658DKWBGznrWxN9wEJVTKkdebkdDEUwuuM893zGUdW04LSs+smd528NKPttgku5GqRQK5Abx78t/PMrcY6ZTAx09kYe6dd88EzOgmXvsO1D0Z59RK5QBun7vWjRXTqnw/jn+vXjHYzmKmTQVQDozWz22WvcZMi2CDMFwwWhhq34A3GC6NMuprHmQFug97iLt0x+YJcG+YDxJ3tkiEEaeiL6lbnnxZ6Cwds46vuwBcqdNXzqGSefuEvO+Vo+GjBM69eX9FBGd0NobkFoSEToePTslyzE2R0BnBu8LGiiRJQ3Q0uSWqoXOiEFvq7h7nxBTVLhXZbVRTuGvtCqOWxCpXm3+p3ELg/6lLnOr49RFUwYiGRgE1cgXBvhZw/fA5TP7AtnIQvVvfE3thCi3c4S4V8x8hmgbYhHuTALLpAsvjfkRdAT7mYTvBqVwMXdTg1suq7bctqrErcgdp1rRuou4qXTqYsE2ByLRmEx0r75SgpLjO3UrWijavMvtnnzgNPF777lA8A==
```

#### 请求报文body数据
```
{"algorithm":"DESede","encryptedData":"35rSBkNNrwk53Nrd658DKWBGznrWxN9wEJVTKkdebkdDEUwuuM893zGUdW04LSs+smd528NKPttgku5GqRQK5Abx78t/PMrcY6ZTAx09kYe6dd88EzOgmXvsO1D0Z59RK5QBun7vWjRXTqnw/jn+vXjHYzmKmTQVQDozWz22WvcZMi2CDMFwwWhhq34A3GC6NMuprHmQFug97iLt0x+YJcG+YDxJ3tkiEEaeiL6lbnnxZ6Cwds46vuwBcqdNXzqGSefuEvO+Vo+GjBM69eX9FBGd0NobkFoSEToePTslyzE2R0BnBu8LGiiRJQ3Q0uSWqoXOiEFvq7h7nxBTVLhXZbVRTuGvtCqOWxCpXm3+p3ELg/6lLnOr49RFUwYiGRgE1cgXBvhZw/fA5TP7AtnIQvVvfE3thCi3c4S4V8x8hmgbYhHuTALLpAsvjfkRdAT7mYTvBqVwMXdTg1suq7bctqrErcgdp1rRuou4qXTqYsE2ByLRmEx0r75SgpLjO3UrWijavMvtnnzgNPF777lA8A=="}
```

## 7. 接口参数/响应详情

### 地址合法性校验

> POST `/v1/address/check`

**请求参数**

|参数名|必选|类型|说明|
|:----    |:---|:----- |-----   |
|currencyCode |是  |string |币种 ， 请参考 [支持币种](#2 - 支持币种（以ICCBank官网为准）) |
|address |是  |string | 地址    |
|labelAddress |否  |string | 标签地址    |

**返回示例**

```
{
	"code": 200,
	"msg": "success",
	"subCode": "0",
	"subMsg": "ok"
}
```

 **返回参数说明** 

|参数名|类型|说明|
|:-----  |:-----|-----                           |
|code |int   | 请求响应码：200：表示成功，400：失败  |
|subCode |int   | [[业务错误代码]][846626167169774]  |

### 获取代收地址

> POST `/v1/address/agency/create`

**请求参数**

|参数名|必选|类型|说明|
|:----    |:---|:----- |-----   |
|currencyCode |是  |string |币种 请参考 [支持币种](#2 - 支持币种（以ICCBank官网为准）)  |
|count |是  |int | 数量范围 [1-100]    |
|batchNumber |是  |string | 批次号   |

**返回示例**

```
  {
    "code":200,
    "data":[
        {
            "address":"0x7C020f22ec9C0f1cA6E4546685CE7FCde9e730b8",
            "currencyCode":"ETH",
            "labelAddress": ""
        }
    ],
    "msg":"success",
    "subCode":"0",
    "subMsg":"ok"
}
```

**业务响应数据说明**

|参数名|类型|说明|
|:-----  |:-----|-----                           |
|currencyCode |String   |币种 请参考 [支持币种](#2 - 支持币种（以ICCBank官网为准）) |
|address |String   |地址  |
|labelAddress |String   |标签地址，memo   |

### 代收通知

**请求参数**

|字段|类型|描述|
|:----    |:-------    |:-------    |
|businessNo |String   |平台订单号，唯一  |
|currencyCode |String   |币种  |
|confirmations |String   |确认次数  |
|txid |String   |交易hash  |
|address |String   |地址  |
|labelAddress |String   |标签地址  |
|amount |String   |数量  |
|status |String   |状态： 0-"处理中"， 1-"成功"， 2-"失败" |

- 说明：各个币种均为处理精度之后的数量。代收异步通知地址，需要在申请API的时候正确填入，否则无法接受到平台的异步通知。

-  响应说明
接收到平台通知之后，响应纯文本`success`表示成功，`error`表示失败


### 代付地址注册

> POST `/v1/agentPay/addAddress`

**请求参数**

|参数名|必选|类型|说明|
|:----    |:---|:----- |-----   |
|currencyCode     |是  |string | 币种  请参考 [支持币种](#2 - 支持币种（以ICCBank官网为准）)  |
|address     |是  |string | 代付地址    |
|labelAddress     |否  |string | 标签地址,如XRP和EOS，这两种币的提币申请该字段可选，起他类型币种不填    |

**返回示例**

```
{
    "code":200,
    "msg":"success",
    "subCode":"0",
    "subMsg":"ok"
}
```

**返回参数说明**

|参数名|类型|说明|
|:-----  |:-----|-----                           |
|code |int   | 请求响应码：200：表示成功，400：失败  |
|subCode |int   | [[业务错误代码]][846626167169774]  |



### 代付

> POST `/v1/agentPay/proxyPay`

**请求参数**

|参数名|必选|类型|说明|
|:----    |:---|:----- |-----   |
|userBizId |是  |string |商户订单号，最大长度64位   |
|subject |否  |string | 订单描述，长度最大128    |
|currencyCode     |是  |string | 币种  请参考 [支持币种](#2 - 支持币种（以ICCBank官网为准）)   |
|address     |是  |string | 代付地址    |
|labelAddress     |否  |string | 标签地址,如XRP和EOS，这两种币的提币申请该字段可选，起他类型币种不填    |
|amount     |是  |string | 数量    |
|notifyUrl     |否  |string | 通知地址（请正确填写，否则无法接收通知）    |

**返回示例**

```
  {
	"code": 200,
	"data": {
		"address": "0xC1b9276626e78447ce728Fd030145d8d21E3619E",
		"amount": "0.0018",
		"businessNo": 1047741661072334848,
		"currencyCode": "IONC",
		"fee": "0.00002",
		"labelAddress": "",
		"status": 1,
		"userBizId": "MCHTEST1594123781344",
		"userId": 1
	},
	"msg": "HTTP_OK",
	"subCode": "0",
	"subMsg": "success"
}
```

**业务参数说明**

|参数名|类型|说明|
|:-----  |:-----|-----                           |
|businessNo |String   |平台订单号  |
|currencyCode |String   |币种 请参考 [支持币种](#2 - 支持币种（以ICCBank官网为准）)  |
|userBizId |String   |商户订单号  |
|address |String   |地址  |
|labelAddress |String   |标签地址  |
|amount |String   |数量  |
|fee |String   |手续费  |
|status |String   |状态， 1:申请成功，2:申请失败 |



### 代付订单查询
> POST `/v1/agentPay/query`

**请求参数** 

|参数名|必选|类型|说明|
|:----    |:---|:----- |-----   |
|userBizId |是  |string |商户订单号   |

**返回示例**

```
  {
	"code": 200,
	"data": {
		"address": "rU5eGrf4sZhUmtH3JoYQNUNZqcds59qGsi",
		"amount": "0.001",
		"businessNo": 1044436267205009408,
		"confirmations": 19941,
		"currencyCode": "XRP",
		"fee": "0.00002",
		"feeCurrency": "XRP",
		"labelAddress": "353757",
		"status": 1,
		"txid": "AB5AA76B4B98C6ACFC67AFC9CD5523D22D2CA5E6EFB29A1481DD35D96D79B0BE",
		"userBizId": "MCHTEST1593335715664"
	},
	"msg": "HTTP_OK",
	"subCode": "0",
	"subMsg": "success"
}
```

**业务参数说明**

|参数名|类型|说明|
|:-----  |:-----|-----                           |
|businessNo |String   |平台订单号  |
|currencyCode |String   |币种  |
|userBizId |String   |商户订单号  |
|confirmations |String   |确认次数  |
|txid |String   |交易hash  |
|address |String   |地址  |
|labelAddress |String   |标签地址  |
|amount |String   |数量  |
|fee |String   |手续费  |
|feeCurrency |String   |手续费币种  |
|status |String   |状态， 0, "处理中" 1, "成功" 2, "失败" |


### 代付通知
> POST `/v1/agentPay/query`

**请求参数**

|字段|类型|描述|
|:----    |:-------    |:-------    |
|businessNo |String   |平台订单号  |
|currencyCode |String   |币种  |
|userBizId |String   |商户订单号  |
|confirmations |String   |确认次数  |
|txid |String   |交易hash  |
|address |String   |地址  |
|labelAddress |String   |标签地址  |
|amount |String   |数量  |
|fee |String   |手续费  |
|feeCurrency |String   |手续费币种  |
|status |String   |状态， 0, "处理中" 1, "成功" 2, "失败" |

-  响应说明
接收到平台通知之后，响应纯文本`success`表示成功，`error`表示失败



### 账户余额查询
> POST `/v1/mch/getBalance`

**请求参数**

|参数名|必选|类型|说明|
|:----    |:---|:----- |-----   |
|currencyCode |否  |string |币种 请参考 [支持币种](#2 - 支持币种（以ICCBank官网为准）)  |
|accountType |否  |string | 账户类型 ,默认为空 1:"代付",2: "代收",3: "存币",4: "提币" |


**返回示例**

```
{
	"code": 200,
	"data": {
		"rows": [
			{
				"accountType": 1,
				"availableBalance": "99.99256",
				"currencyCode": "XRP",
				"frozenBalance": "0.00224"
			}
		]
	},
	"msg": "HTTP_OK",
	"subCode": "0",
	"subMsg": "success"
}

```

**业务参数说明**

|参数名|类型|说明|
|:-----  |:-----|-----                           |
|currencyCode |string   |币种 请参考 [支持币种](https://www.showdoc.cc/846626167169774?page_id=4705555980831752 "币种列表")|
|accountType |long   |账户类型 1:"代付",2: "代收",3: "存币",4: "提币"|
|availableBalance |string   | 可用余额  |
|frozenBalance |string   |冻结余额 |


### 通知计划
为避免接收方服务器异常状态，平台制定以下通知计划。
间隔时间：`1s, 5s, 30s, 5m, 10m`
平台总共进行5次通知，5次全部失败请联系对应运营人员或客服

