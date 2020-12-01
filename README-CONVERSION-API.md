**兑换相关接口**
- 2.1 - [兑换币种列表](#8-1兑换币种列表)
- 2.2 - [矿工费列表](#8-2矿工费列表)
- 2.3 - [兑换利率查询](#8-3兑换利率查询)
- 2.4 - [根据前端订单id查询状态](#8-4根据前端订单id查询状态)
- 2.5 - [根据前端订单id查询详情](#8-5根据前端订单id查询详情)
- 2.6 - [获取兑换订单列表](#8-6获取兑换订单列表)
- 2.7 - [创建固定汇率订单](#8-7创建固定汇率订单)
- 2.8 - [创建浮动汇率订单](#8-8创建浮动汇率订单)

### 8-1兑换币种列表

>  POST `/v1/conversion/getConversionCurrencies` 

> sdk方法getConversionCurrencies` 

**参数：** 

| 参数名       | 必选 | 类型   | 说明                                                         |
| :----------- | :--- | :----- | ------------------------------------------------------------ |
| currencyCode | 是   | string | 币种 请参考 [支持币种](#2-支持币种)  |


 **返回示例**

``` 
{
    "code":200,
    "data":[
        {
            "code":"ETH/USDT_OMNI",
            "currency":"ETH",
            "targetCurrency":"USDT_OMNI"
        },
        {
            "code":"BTC/USDT_OMNI",
            "currency":"BTC",
            "targetCurrency":"USDT_OMNI"
        }
    ],
    "msg":"HTTP_OK",
    "subCode":"0",
    "subMsg":"success"
}
```

**返回参数说明** 

|参数名|类型|说明|
|:-----  |:-----|-----                           |
|code           |string   | 兑换币对code  |
|currency       |string   | 兑换币对currency  |
|targetCurrency |string   | 兑换币对targetCurrency  |



### 8-2矿工费列表

>  POST `/v1/conversion/getCurrencyMineFeeList` 

> sdk方法getCurrencyMineFeeList` 

**参数：** 
|参数名|必选|类型|说明|
|:----    |:---|:----- |-----   |


 **返回示例**

``` 
{
    "code":200,
    "data":[
        {
            "currency":"ETH",
            "feeCoin":"ETH",
            "feeMaxNum":0.005,
            "feeMinNum":0.0001,
            "feeNum":0.0002,
            "linkType":"ethereum"
        },
        {
            "currency":"BTC",
            "feeCoin":"BTC",
            "feeMaxNum":0.0005,
            "feeMinNum":0.0001,
            "feeNum":0.0002,
            "linkType":"bitcoin"
        },
        {
            "contractAddress":"0xa0b86991c6218b36c1d19d4a2e9eb0ce3606eb48",
            "currency":"USDC",
            "feeCoin":"ETH",
            "feeMaxNum":0.0005,
            "feeMinNum":0.0001,
            "feeNum":0.0002,
            "linkType":"ethereum"
        }
    ],
    "msg":"HTTP_OK",
    "subCode":"0",
    "subMsg":"success"
}
```

**返回参数说明** 

| 参数名          | 类型   | 说明       |
| :-------------- | :----- | ---------- |
| contractAddress | string | 合约地址   |
| currency        | string | 支付币种   |
| feeCoin         | string | feeCoin    |
| feeMaxNum       | string | 最大矿工费 |
| feeMinNum       | string | 最小矿工费 |
| feeNum          | string | 矿工费     |
| linkType        | string | 链类型     |



### 8-3兑换利率查询 

>  POST `/v1/conversion/getConversionRate` 

> sdk方法getConversionRate` 

**参数：** 
|参数名|必选|类型|说明|
|:----    |:---|:----- |-----   |
|code |是  |string |币对code   |
|fixedRate |是  |bool | 是否是固定利率    |
|amountFrom |否  |number | from币种输入金额    |
|amountTo |否  |number | to币种输入金额    |


注意：
1. 获取固定利率时amountFrom和amountTo只能传一个
2. 获取浮动利率时amountFrom不能为空，amountTo的值必须为空
3. amountFrom和amountTo的传值不为空时，必须大于0


 **返回示例**

``` 
{
    "code": "200",
    "msg": "200",
    "data": {
        "rate": "9978.861068836",
        "rateId": "e78f80e3f59ad23209411d1f3ad61df1c69acdd364ec74feed86569b4a99f30357760b6ce6",
        "minFrom": "0.00465000",
        "maxFrom": "1.50317755",
        "amountFrom": "46.41",
        "amountTo": "14999.99",
		"conversionFee": "14999.99",
		"networkFee": "14999.99"
    }
}
```

**返回参数说明** 

|参数名|类型|说明|
|:-----  |:-----|-----                           |
|rate |BigDecimal   |兑换汇率  |
|rateId |Long   |兑换汇率id（兑换提交时要传到后端）  |
|minFrom |BigDecimal   |支付币种输入最小数量  |
|maxFrom |BigDecimal   |支付币种输入最大数量  |
|amountFrom |BigDecimal   |支付金额  |
|amountTo |BigDecimal   |兑换金额  |
|conversionFee |BigDecimal   |兑换手续费  |
|networkFee |BigDecimal   |网络费（矿工费）  |



### 8-4根据前端订单id查询状态 

>  POST `/v1/conversion/getConversionStatus` 

> sdk方法getConversionStatus` 

**参数：** 

|参数名|必选|类型|说明|
|:----    |:---|:----- |-----   |
|thirdOrderId   |是  |Long | 订单id    |


 **返回示例**

``` 
{
    "code":200,
    "data":{
        "status":"4"
    },
    "msg":"HTTP_OK",
    "subCode":"0",
    "subMsg":"success",
    "success":true
}
```

**返回参数说明** 

|参数名|类型|说明|
|:-----  |:-----|-----                           |
|status  |string   | 订单状态（0-等待中 1-确认中 2-兑换中 3-发送中 4-已完成 5-已失败 6-已退款 7-逾期 8-暂停 9-已过期） |



### 8-5根据前端订单id查询详情 

>  POST `/v1/conversion/getConversionDetail` 

> sdk方法getConversionDetail` 

**参数：** 

|参数名|必选|类型|说明|
|:----    |:---|:----- |-----   |
|thirdOrderId |是  |Long | 订单id    |


 **返回示例**

``` 
{
    "code":200,
    "data":{
        "minerFee":0,
        "refundStatus":4,
        "type":0,
        "createdOn":1601018748801,
        "platformFee":0,
        "enabled":true,
        "actualConversionRate":342.0627,
        "refundAddress":"0x789251e2388BfcF1ff19a219d859f6F9a07ebc57",
        "payinId":"",
        "amountTo":342.0627,
        "refundLabelAddress":"",
        "thirdOrderId":"1076661237708713984",
        "id":1076661240351125504,
        "amountExpectedFrom":1,
        "payinAddress":"0x789251e2388BfcF1ff19a219d859f6F9a07ebc57",
        "orderSource":"api",
        "launchConversionRate":342.0627,
        "proxyToId":"",
        "amountExpectedTo":342.0627,
        "amountFrom":1,
        "updatedOn":1601018874815,
        "payTill":1601020548801,
        "payinLabelAddress":"",
        "userId":1,
        "addressTo":"1H6RheRDg4b3PMhTimGzuZ2YJ8pcfToaRt",
        "labelAddressTo":"",
        "currencyTo":"USDT_OMNI",
        "currencyFrom":"ETH",
        "status":1
    },
    "msg":"HTTP_OK",
    "subCode":"0",
    "subMsg":"success",
    "success":true
}
```

**返回参数说明** 

|参数名|类型|说明|
|:-----  |:-----|-----                           |
|thirdOrderId  |string   | 第三方id |
|orderSource  |string   | 订单来源（web、api。。） |
|currencyFrom  |string   | 支付币种 |
|currencyTo  |string   | 兑换币种 |
|addressTo  |string   | 兑换地址 |
|labelAddressTo  |string   | 兑换地址标签 |
|proxyToId  |string   | 兑换代付id |
|payTill  |datetime   | 截止支付时间（带时区的时间字符串） |
|payinAddress  |string   | 平台收款地址 |
|payinLabelAddress  |string   | 平台收款地址标签 |
|payinId  |string   | 平台代收记录id |
|refundAddress  |string   | 退款地址 |
|refundLabelAddress  |string   | 退款地址标签 |
|amountExpectedFrom  |number   | 预计支付 |
|amountExpectedTo  |number   | 预计收到 |
|amountFrom  |number   | 实际支付 |
|amountTo  |number   | 实际收到（包含矿工费） |
|apiFee  |number   | api佣金(目前没用) |
|platformFee  |number   | 平台费用（总费用） |
|minerFee  |number   | 预计矿工费（本币数量） |
|actualMinerFee  |number   | 实际旷工费（token币时位主链币数量） |
|launchConversionRate  |number   | 发起兑换时汇率 |
|actualConversionRate  |number   | 实际兑换利率 |
|type  |string   | 0-固定利率 1-浮动利率 |
|status  |number   | 0-等待中 1-确认中 2-兑换中 3-发送中 4-已完成 5-已失败 6-已退款 7-逾期 8-暂停 9-已过期 |
|refundStatus  |number   | 0-待退款 1-退款中 2-退款成功 3-无|
|enabled  |number   | true-启用 false-禁用|
|createdOn  |datetime   | 创建时间 |
|updatedOn  |datetime   | 编辑时间 |


### 8-6获取兑换订单列表

> POST `/v1/conversion/getConversionList`

> sdk方法 ConversionApiClient.getConversionOrderList

**请求参数**

|参数名|必选|类型|说明|
|:----    |:---|:----- |-----   |
|orderSource |否  |string | 订单来源（web、api。。）    |
|currencyFrom  |否  |string | 支付币种    |
|currencyTo    |否  |string | 兑换币种    |
|rateType   |否  |number | 0-固定利率 1-浮动利率    |
|status   |否  |number | 0-等待中 1-确认中 2-兑换中 3-发送中 4-已完成 5-已失败 6-已退款 7-逾期 8-暂停 9-已过期    |
|refundStatus   |否  |number | 0-待退款 1-退款中 2-退款成功 3-无    |
|enabled   |否  |bool | true-启用 false-禁用    |
|pageIndex   |否  |number |  分页下标，默认1   |
|pageSize   |否  |number |  每页数量，默认10   |

**返回示例**

``` 
{
    "code":200,
    "data":{
        "pageNo":1,
        "pageSize":10,
        "rows":[
            {
                "actualConversionRate":342.0627,
                "addressTo":"1H6RheRDg4b3PMhTimGzuZ2YJ8pcfToaRt",
                "amountExpectedFrom":1,
                "amountExpectedTo":342.0627,
                "amountFrom":1,
                "amountTo":342.0627,
                "createdOn":1601018748801,
                "currencyFrom":"ETH",
                "currencyTo":"USDT_OMNI",
                "enabled":true,
                "id":1076661240351125504,
                "labelAddressTo":"",
                "launchConversionRate":342.0627,
                "minerFee":0,
                "orderSource":"api",
                "payTill":1601020548801,
                "payinAddress":"0x789251e2388BfcF1ff19a219d859f6F9a07ebc57",
                "payinId":"",
                "payinLabelAddress":"",
                "platformFee":0,
                "proxyToId":"",
                "refundAddress":"0x789251e2388BfcF1ff19a219d859f6F9a07ebc57",
                "refundLabelAddress":"",
                "refundStatus":4,
                "status":1,
                "thirdOrderId":"1076661237708713984",
                "type":0,
                "updatedOn":1601018874815,
                "userId":1
            }
        ],
        "totalPage":1,
        "totalRow":1
    },
    "msg":"HTTP_OK",
    "subCode":"0",
    "subMsg":"success"
}
```

**返回参数说明** 

|参数名|类型|说明|
|:-----  |:-----|-----                           |
|thirdOrderId  |string   | 第三方id |
|orderSource  |string   | 订单来源（web、api。。） |
|currencyFrom  |string   | 支付币种 |
|currencyTo  |string   | 兑换币种 |
|addressTo  |string   | 兑换地址 |
|labelAddressTo  |string   | 兑换地址标签 |
|proxyToId  |string   | 兑换代付id |
|payTill  |datetime   | 截止支付时间（带时区的时间字符串） |
|payinAddress  |string   | 平台收款地址 |
|payinLabelAddress  |string   | 平台收款地址标签 |
|payinId  |string   | 平台代收记录id |
|refundAddress  |string   | 退款地址 |
|refundLabelAddress  |string   | 退款地址标签 |
|amountExpectedFrom  |number   | 预计支付 |
|amountExpectedTo  |number   | 预计收到 |
|amountFrom  |number   | 实际支付 |
|amountTo  |number   | 实际收到（包含矿工费） |
|apiFee  |number   | api佣金(目前没用) |
|platformFee  |number   | 平台费用（总费用） |
|minerFee  |number   | 预计矿工费（本币数量） |
|actualMinerFee  |number   | 实际旷工费（token币时位主链币数量） |
|launchConversionRate  |number   | 发起兑换时汇率 |
|actualConversionRate  |number   | 实际兑换利率 |
|type  |string   | 0-固定利率 1-浮动利率 |
|status  |number   | 0-等待中 1-确认中 2-兑换中 3-发送中 4-已完成 5-已失败 6-已退款 7-逾期 8-暂停 9-已过期 |
|refundStatus  |number   | 0-待退款 1-退款中 2-退款成功 3-无|
|enabled  |number   | true-启用 false-禁用|
|createdOn  |datetime   | 创建时间 |
|updatedOn  |datetime   | 编辑时间 |


### 8-7创建固定汇率订单

> POST `/v1/conversion/createFixRateConversion`

> sdk方法 ConversionApiClient.createFixRateConversion

**请求参数**
|参数名|必选|类型|说明|
|:----    |:---|:----- |-----   |
|orderId |是  |string | 订单号    |
|source |是  |string | 来源    |
|rateId |是  |string | 汇率id    |
|code |是  |string | 兑换币对code    |
|payoutAddress     |是  |string | 兑换地址    |
|payoutLabelAddress     |是  |string | 兑换地址标签    |
|refundAddress     |是  |string | 退款地址    |
|refundLabelAddress     |是  |string | 退款地址标签    |
|amountExpectedTo  |与amountExpectedFrom二选一  |string | 预期兑换币种数量    |
|amountExpectedFrom  |与amountExpectedTo二选一  |string | 预期支付币种数量    |

**返回示例**
``` 
{
    "code":200,
    "data":{
        "amountFrom":1,
        "id":1082797901495070720,
        "payTill":1602483642648,
        "payinAddress":"1NJGyKsF9eqi1gCFAME6fnem6imyuwKACd",
        "payinLabelAddress":""
    },
    "msg":"HTTP_OK",
    "subCode":"0",
    "subMsg":"success"
}
```

**返回参数说明** 
|参数名|类型|说明|
|:-----  |:-----|-----       |
|id     |String |  交易号   |
|payinAddress     |String |  用户支付地址   |
|payinLableAddress     |String | 支付地址扩展字段（如EOS的memo）    |
|amountFrom     |String |  支付币种数量   |
|payTill     |String |   支付截止时间  |

**备注**
|特殊处理code|说明|
|:-----  |:----- 
|-32602      |  汇率超时 （后端提示不翻译，此code码前端特殊处理）  |
 
### 8-8创建浮动汇率订单

> POST `/v1/conversion/createFloatRateConversion`

> sdk方法 ConversionApiClient.createFloatRateConversion

**请求参数**


**返回示例**
``` 
{
    "code":200,
    "data":{
        "amountFrom":1,
        "id":1082798745166737408,
        "payTill":1602568443797,
        "payinAddress":"1CuZNkUpYmZrDnxdRTavYPQTGs7AiA28P1",
        "payinLabelAddress":""
    },
    "msg":"HTTP_OK",
    "subCode":"0",
    "subMsg":"success"
}
```

**返回参数说明** 
|参数名|类型|说明|
|:-----  |:-----|-----       |
|id     |String |  交易号   |
|payinAddress     |String |  用户支付地址   |
|payinLableAddress     |String | 支付地址扩展字段（如EOS的memo）    |
|amountFrom     |String |  支付币种数量   |
|payTill     |String |   支付截止时间  |

**备注**

|特殊处理code|说明|
|:-----  |:----- 
|-32602      |  汇率超时 （后端提示不翻译，此code码前端特殊处理）  |


