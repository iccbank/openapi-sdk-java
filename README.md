# ICCBank OpenAPI Documents

## 1. 接口列表

- 1.1 - [地址合法性校验](#7-1地址合法性校验)
- 1.2 - [地址合法性校验-带地址标签](#7-2地址合法性校验-带地址标签)
- 1.3 - [代付地址注册](#7-3代付地址注册)
- 1.4 - [创建代收地址](#7-4创建代收地址)
- 1.5 - [代付](#7-5代付)
- 1.6 - [代付-可以指定矿工费](#7-6代付-可以指定矿工费)
- 1.7 - [代付订单查询](#7-7代付订单查询)
- 1.8 - [获取账户余额列表](#7-8获取账户余额列表)
- 1.9 - [代币搜索](#7-9代币搜索)
- 1.10 - [添加代币](#7-10添加代币)
- 1.11 - [获取币种费用信息](#7-11获取币种费用信息)
- 1.12 - [查询主链币开通币种列表](#7-12查询主链币开通币种列表)
- 1.13 - [查询单个币种信息](#7-13查询单个币种信息)
- 1.14 - [查询账户余额列表-指定币种](#7-14查询账户余额列表-指定币种)
- 1.15 - [查询账户余额-指定币种和账户类型](#7-15查询账户余额-指定币种和账户类型)
- 1.16 - [查询账户总资产-指定币种](#7-16查询账户总资产-指定币种)



## 2. 支持币种

#### 主链币

|Code|Desc|
|:----    |:------- |
|BTC    |比特币    |
|BCH    |比特现金    |
|BSV    |比特币SV    |
|LTC    |莱特币    |
|ETH    |以太坊    |
|ETC    |以太经典    |
| IONC   | 离子币   |
| FIL   |  文件币  |
|  XMR  | 门罗币   |
|  DASH  |  达世币  |
|  TRX  |  波场  |
|  XRP  |  瑞波  |

#### 代币，具体以[ICCBank](https://www.iccbank.net)官网为准
|Code|Desc|
|:----    |:------- |
|USDT_OMNI   |  泰达币  |
|USDT_ERC20  |  泰达币  |
|BNB  |币安币  |
|LINK  |ChainLink  |
|TRX  |波场  |
|OKB  |OK币  |
|DAI  |Dai Stablecoin  |
|HT  |火币积分  |
|MKR  |Maker  |
|OMG  |嫩模币  |
|SEELE  |元一代币  |
|UNI  |Uniswap  |

## 3. 请求方式
- HTTPS + JSON

## 4. SDK列表
- [Java](./README-JAVA.md)

## 5. 公共请求参数

| Header        | 必选   |  类型  | 说明 |
|:--------   |:-----  |:----  |------ |
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

### 7-1地址合法性校验

> POST `/v1/address/check`

> sdk方法 ApiClient.addressCheck

**请求参数**

|参数名|必选|类型|说明|
|:----    |:---|:----- |-----   |
|currencyCode |是  |string |币种 ， 请参考 [支持币种](#2-支持币种) |
|address |是  |string | 地址    |

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
|subCode |int   | [业务状态码](#业务状态码)  |

### 7-2地址合法性校验-带地址标签

> POST `/v1/address/check`

> sdk方法 ApiClient.addressCheck

**请求参数**

|参数名|必选|类型|说明|
|:----    |:---|:----- |-----   |
|currencyCode |是  |string |币种 ， 请参考 [支持币种](#2-支持币种) |
|address |是  |string | 地址    |
|labelAddress |是  |string | 标签地址    |

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
|subCode |int   | [业务状态码](#业务状态码)  |

### 7-3代付地址注册

> POST `/v1/agentPay/addAddress`

> sdk方法 ApiClient.agentPayAddAddress

**请求参数**

|参数名|必选|类型|说明|
|:----    |:---|:----- |-----   |
|currencyCode     |是  |string | 币种  请参考 [支持币种](#2-支持币种)  |
|address     |是  |string | 代付地址    |
|labelAddress     |否  |string | 标签地址,如XRP和EOS，这两种币的提币申请该字段可选，其它类型币种不填    |

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
|subCode |int   | [业务状态码](#业务状态码)  |

### 7-4创建代收地址

> POST `/v1/address/agency/create`

> sdk方法 ApiClient.createAgencyRechargeAddress

**请求参数**

|参数名|必选|类型|说明|
|:----    |:---|:----- |-----   |
|currencyCode |是  |string |币种 请参考 [支持币种](#2-支持币种)  |
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
|currencyCode |String   |币种 请参考 [支持币种](#2-支持币种) |
|address |String   |地址  |
|labelAddress |String   |标签地址，memo   |



### 7-5代付

> POST `/v1/agentPay/proxyPay`

> sdk方法 ApiClient.agencyWithdraw

**请求参数**

|参数名|必选|类型|说明|
|:----    |:---|:----- |-----   |
|userBizId |是  |string |商户订单号，最大长度64位   |
|subject |否  |string | 订单描述，长度最大128    |
|currencyCode     |是  |string | 币种  请参考 [支持币种](#2-支持币种)   |
|address     |是  |string | 代付地址    |
|labelAddress     |否  |string | 标签地址,如XRP和EOS，这两种币的提币申请该字段可选，其它类型币种不填    |
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
|currencyCode |String   |币种 请参考 [支持币种](#2-支持币种)  |
|userBizId |String   |商户订单号  |
|address |String   |地址  |
|labelAddress |String   |标签地址  |
|amount |String   |数量  |
|fee |String   |手续费  |
|status |String   |状态， 1:申请成功，2:申请失败 |


#### 代付通知

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
|minerFee    |String   |矿工费（商户传矿工费时才有值）  |
|status |String   |状态， 0, "处理中" 1, "成功" 2, "失败" |

-  响应说明
接收到平台通知之后，响应纯文本`success`表示成功，`error`表示失败


### 7-6代付-可以指定矿工费

> POST `/v1/agentPay/proxyPay2 `

> sdk方法 ApiClient.agencyWithdrawWithMinerFee

**请求参数**

|参数名|必选|类型|说明|
|:----    |:---|:----- |-----   |
|userBizId |是  |string |商户订单号，最大长度64位   |
|subject |否  |string | 订单描述，长度最大128    |
|currencyCode     |是  |string | 币种  请参考 [支持币种](#2-支持币种)   |
|address     |是  |string | 代付地址    |
|labelAddress     |否  |string | 标签地址,如XRP和EOS，这两种币的提币申请该字段可选，其它类型币种不填    |
|amount     |是  |string | 数量     |
|minerFee   |是  |string | 矿工费   |
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
        "userId": 1,
        "minerFee": "0.0001"
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
|currencyCode |String   |币种 请参考 [支持币种](#2-支持币种)  |
|userBizId |String   |商户订单号  |
|address |String   |地址  |
|labelAddress |String   |标签地址  |
|amount |String   |数量  |
|fee |String   |手续费  |
|minerFee |String   |矿工费  |
|status |String   |状态， 1:申请成功，2:申请失败 |


### 7-7代付订单查询
> POST `/v1/agentPay/query`

> sdk方法 ApiClient.queryAgencyWithdrawOrder

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
        "minerFee": "0.0001",
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
|minerFee  |String   |矿工费（商户传矿工费时才有值）|
|status |String   |状态， 0, "处理中" 1, "成功" 2, "失败" |


### 7-8获取账户余额列表
> POST `/v1/mch/getBalance`

> sdk方法 ApiClient.getBalances

**请求参数**

|参数名|必选|类型|说明|
|:----    |:---|:----- |-----   |
|- |-  |- |-  |


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
|currencyCode |string   |币种 请参考 [支持币种](#2-支持币种)|
|accountType |long   |账户类型 1:"代付",2: "代收",3: "存币",4: "提币"|
|availableBalance |string   | 可用余额  |
|frozenBalance |string   |冻结余额 |


### 7-9代币搜索
> POST `/v1/currency/search`

> sdk方法 ApiClient.currencySearch

**请求参数**

|参数名|必选|类型|说明|
|:----    |:---|:----- |-----   |
|searchType |是  |int | 搜索类型：1-币种搜索、2-合约地址搜索|
|keywords |是  |string | 关键词 |


**返回示例**

```
{
    "code": 200,
    "data": [
        {
            "name":"Tether USD",
            "symbol":"USDT",
            "code":"USDT_ERC20",
            "linkType":"ethereum",
            "icon":"base64;",
            "contractAddress":"0xdAC17F958D2ee523a2206206994597C13D831ec7",
            "supportLabelAddress": 0,
            "chainCode": "ETH",
            "maxDecimals": 6
        },
        {
            "name":"Tether Omni",
            "symbol":"USDT",
            "code":"USDT_OMNI",
            "linkType":"bitcoin",
            "icon":"base64;",
            "contractAddress":"",
            "supportLabelAddress": 0,
            "chainCode": "BTC",
            "maxDecimals": 6
        }
    ],
    "msg": "HTTP_OK",
    "subCode": "0",
    "subMsg": "success"
}

```

**业务参数说明**

|参数名|类型|说明|
|:-----  |:-----|-----|
|name |string   | 名称 |
|symbol |long   | 代币标识（不唯一）|
|code |string   | 币种代码（唯一）  |
|linkType |string   |链类型 |
|icon |string   |币种图标 |
|contractAddress |string   |合约地址 |
|supportLabelAddress |int   | 是否支持标签地址：0-不支持，1-支持  |
|chainCode |string   | 主链币种代码  |
|maxDecimals |int   | 币种单位最大的精度  |

### 7-10添加代币
> POST `/v1/currency/addToken`

> sdk方法 ApiClient.currencyAddToken

**请求参数**

|参数名|必选|类型|说明|
|:----    |:---|:----- |-----   |
|linkType |是  |string | 链类型|
|contractAddress |是  |string | 合约地址 |


**返回示例**

```
{
    "code": "200",
    "data": {
        "name":"Tether USD",
        "symbol":"USDT",
        "code":"USDT_ERC20",
        "linkType":"ethereum",
        "icon":"http://xxx.xx.com/a.jpg",
        "contractAddress":"0xdAC17F958D2ee523a2206206994597C13D831ec7",
        "supportLabelAddress": 0,
        "chainCode": "ETH",
        "maxDecimals": 6
    },
    "msg": "HTTP_OK",
    "subCode": "0",
    "subMsg": "success"
}

```

**业务参数说明**

|参数名|类型|说明|
|:-----  |:-----|-----    |
|name |string   |币种名称  |
|symbol |string   |币种标识（不唯一）  |
|code |string   |币种代码（唯一）  |
|linkType |string   |链类型  |
|icon |string   |图标  |
|contractAddress |string   | 合约地址  |
|supportLabelAddress |int   | 是否支持标签地址：0-不支持，1-支持  |
|chainCode |string   | 主链币种代码  |
|maxDecimals |int   | 币种单位最大的精度  |



### 7-11获取币种费用信息

> POST `/v1/currency/bizFee`

> sdk方法 ApiClient.getCurrencyFee

**请求参数**

| 参数名       | 必选 | 类型   | 说明                                       |
| :----------- | :--- | :----- | ------------------------------------------ |
| currencyCode | 是   | string | 币种代码 ， 请参考 [支持币种](#2-支持币种) |

**返回示例**

```
{
    "code": "200",
    "msg": "操作成功",
    "data": {
        "feeCurrencyCode": "ETH",
        "bizFee": "0.001",
        "minMinerFee": "0.0005",
        "maxMinerFee": "0.01"
    }
}
```

 **返回参数说明** 

| 参数名          | 类型   | 说明         |
| --------------- | :----- | ------------ |
| feeCurrencyCode | String | 费用币种代码 |
| bizFee          | String | 提币手续费   |
| minMinerFee     | String | 最小矿工费(上链交易时矿工费最小值)   |
| maxMinerFee     | String | 最大矿工费(上链交易时矿工费最大值)   |



### 7-12查询主链币开通币种列表

>  POST `/v1/currency/queryChainList `

> sdk方法 ApiClient.queryCurrencyChainList

**参数：**

| 参数名 | 必选 | 类型 | 说明 |
| :----- | :--- | :--- | ---- |
| -      | -    | -    | -    |


 **返回示例**

```
{
    "code": "200",
    "msg": "操作成功",
    "data": [
        {
            "name":"Tether USD",
            "symbol":"USDT",
            "code":"USDT_ERC20",
            "linkType":"ethereum",
            "icon":"base64;",
            "contractAddress":"0xdAC17F958D2ee523a2206206994597C13D831ec7",
            "supportLabelAddress": 0,
            "chainCode": "ETH",
            "maxDecimals": 6
        }
    ]
}
```

 **返回参数说明**

| 参数名              | 类型   | 说明               |
| :------------------ | :----- | ------------------ |
| name                | string | 币种名称           |
| symbol              | string | 币种标识（不唯一） |
| code                | string | 币种代码（唯一）   |
| linkType            | string | 链类型             |
| icon                | string | 图标               |
| contractAddress     | string | 合约地址           |
| supportLabelAddress | int    | 是否支持标签地址   |
| chainCode           | string | 主链币种代码       |
| maxDecimals         | int    | 币种单位最大的精度 |



### 7-13查询单个币种信息

>  POST `/v1/currency/getByCode `

> sdk方法 ApiClient.getCurrencyByCode

**请求参数：**

| 参数名       | 必选 | 类型   | 说明                                       |
| :----------- | :--- | :----- | ------------------------------------------ |
| currencyCode | 是   | string | 币种代码 ， 请参考 [支持币种](#2-支持币种) |



 **返回示例**

```
{
    "code": "200",
    "msg": "操作成功",
    "data": [{
        "name":"Tether USD",
        "symbol":"USDT",
        "code":"USDT_ERC20",
        "linkType":"ethereum",
        "icon":"base64;",
        "contractAddress":"0xdAC17F958D2ee523a2206206994597C13D831ec7",
        "supportLabelAddress": 0,
        "chainCode": "ETH",
        "maxDecimals": 6
        }
    ]
}
```

 **返回参数说明**

| 参数名              | 类型   | 说明               |
| :------------------ | :----- | ------------------ |
| name                | string | 币种名称           |
| symbol              | string | 币种标识（不唯一） |
| code                | string | 币种代码（唯一）   |
| linkType            | string | 链类型             |
| icon                | string | 图标               |
| contractAddress     | string | 合约地址           |
| supportLabelAddress | int    | 是否支持标签地址   |
| chainCode           | string | 主链币种代码       |
| maxDecimals         | int    | 币种单位最大的精度 |



### 7-14查询账户余额列表-指定币种

**请求URL：** 
> POST ` /v1/mch/getBalance `

> sdl方法 ApiClient.getBalancesForCurrencyCode

**参数：** 

| 参数名       | 必选 | 类型   | 说明                                                         |
| :----------- | :--- | :----- | ------------------------------------------------------------ |
| currencyCode | 是   | string | 币种 请参考 [支持币种](#2-支持币种)  |


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

 **返回参数说明** 

| 参数名           | 类型   | 说明                                                         |
| :--------------- | :----- | ------------------------------------------------------------ |
| availableBalance | string | 可用余额       |
| currencyCode     | string | 币种 请参考 [支持币种](#2-支持币种)  |
| frozenBalance    | string | 冻结余额   |
| accountType      | long   | 账户类型 1:"代付",2: "代收",3: "存币",4: "提币"              |



### 7-15查询账户余额-指定币种和账户类型
**请求URL：** 

> POST ` /v1/mch/getBalance `

> sdl方法 ApiClient.getBalancesForCurrencyCodeAndAccountType

**参数：** 

| 参数名       | 必选 | 类型   | 说明                                                         |
| :----------- | :--- | :----- | ------------------------------------------------------------ |
| currencyCode | 是   | string |币种 请参考 [支持币种](#2-支持币种)  |
|accountType |是  |string | 账户类型 ,默认为空 1:"代付",2: "代收",3: "存币",4: "提币" |

 **返回示例**

``` 
{
    "code": 200,
    "data": {
        "accountType": 1,
        "availableBalance": "99.99256",
        "currencyCode": "XRP",
        "frozenBalance": "0.00224"
    },
    "msg": "HTTP_OK",
    "subCode": "0",
    "subMsg": "success"
}

```

 **返回参数说明** 

| 参数名           | 类型   | 说明                                                         |
| :--------------- | :----- | ------------------------------------------------------------ |
| availableBalance | string | 可用余额                                                     |
| currencyCode     | string | 币种 请参考 [支持币种](#2-支持币种)  |
| frozenBalance    | string | 冻结余额                                                     |
| accountType      | long   | 账户类型 1:"代付",2: "代收",3: "存币",4: "提币"              |


### 7-16查询账户总资产-指定币种

>  POST `/v1/mch/getTotalBalance` 

> sdk方法 ApiClient.getTotalBalancesForCurrencyCode

**参数：** 

| 参数名       | 必选 | 类型   | 说明                                                         |
| :----------- | :--- | :----- | ------------------------------------------------------------ |
| currencyCode | 是   | string | 币种 请参考 [支持币种](#2-支持币种)  |


 **返回示例**

``` 
{
    "code": 200,
    "data": {
        "availableBalance": "99.99256",
        "currencyCode": "XRP",
        "frozenBalance": "0.00224"
    },
    "msg": "HTTP_OK",
    "subCode": "0",
    "subMsg": "success"
}
```

**返回参数说明** 

| 参数名           | 类型   | 说明                                                         |
| :--------------- | :----- | ------------------------------------------------------------ |
|currencyCode   |string |币种 请参考 [支持币种](#2-支持币种)  |
| availableBalance | string | 可用余额                                                     |
| frozenBalance    | string | 冻结余额                                   |