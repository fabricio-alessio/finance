package com.fasolutions.finance.application.model

enum class IndicatorCode(
    val formula: String,
    val description: String
) {
    DY("Dividendos pagos no período / Preço ação", "Indicador utilizado para relacionar os proventos pagos por uma companhia e o preço atual de suas ações."),
    LPA("Lucro líquido / Nº de ações", "Indicar se a empresa é ou não lucrativa. Se este número estiver negativo, a empresa está com margens baixas, acumulando prejuízos."),
    VPA("Patrimônio líquido / Nº de ações", "Indica qual o valor patrimonial de uma ação."),
    DIV_EBITDA("Dívida líquida / EBITDA", "Indica quanto tempo seria necessário para pagar a dívida líquida da empresa considerando o EBITDA atual. Indica também o grau de endividamento da companhia."),
    MARG_EBITDA("EBITDA / Receita líquida", "É o percentual da divisão entre o EBITDA (Earnings before interest, taxes, depreciation and amortization) e a receita líquida de uma companhia. Muito útil para se comparar a lucratividade operacional da empresa."),
    ROE("Lucro líquido / Patrimônio líquido", "Mede a capacidade de agregar valor de uma empresa a partir de seus próprios recursos e do dinheiro de investidores."),
    ROIC("(EBIT - Impostos) / (Patrimônio líquido + Endividamento)", "Mede a rentabilidade de dinheiro que uma empresa é capaz de gerar em razão de todo o capital investido, incluindo os aportes por meio de dívidas.")
}