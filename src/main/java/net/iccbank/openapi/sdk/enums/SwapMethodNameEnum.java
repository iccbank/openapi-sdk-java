package net.iccbank.openapi.sdk.enums;

public enum  SwapMethodNameEnum {

    SWAP_EXACT_TOKENS_FOR_TOKENS(1,"swapExactTokensForTokens"),
    SWAP_TOKENS_FOR_EXACT_TOKENS(2,"swapTokensForExactTokens"),
    SWAP_EXACT_ETH_FOR_TOKENS(3,"swapExactETHForTokens"),
    SWAP_TOKENS_FOR_EXACT_ETH(4,"swapTokensForExactETH"),
    SWAP_EXACT_TOKENS_FOR_ETH(5,"swapExactTokensForETH"),
    SWAP_ETH_FOR_EXACT_TOKENS(6,"swapETHForExactTokens");


    private int type;
    private String name;

    SwapMethodNameEnum(int type,String name) {
        this.type = type;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }


}
