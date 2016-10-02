# Cessão de Recebíveis

O problema a ser resolvido neste teste é o de escolha de recebíveis a ceder para minimizar perdas.

Imagine os recebíveis:

| Id | Valor  | Perda |
|:---|-------:|------:|
| 1  | 100.00 | 0.05  |
| 2  |  60.00 | 0.04  |
| 3  |  40.00 | 0.06  |

E você precisa ceder 100 reais. Você cederia 1 ou (2 + 3)?

Agora imagine que você tem 10.000 recebíveis aleatórios, com: 
- 10% entre 50 e 700 reais;
- 80% entre 701 e 2000 reais;
- 10% entre 2001 e 10000 reais.

Com parcelas aleatórias entre 1 e 12 (1/12 para cada número de parcelas possíveis)

E perdas:
- 0,10% para 1 parcela;
- 0,08% para 2 parcelas;
- 0,06% para 3 parcelas;
- 0,04% para 4 parcelas;
- 0,03% para 5 parcelas;
- 0,02% para 6 parcelas; 
- 0,01% de 7 a 12 parcelas.

Faça um programa de escolha qual o conjunto de recebíveis para minimizar perdar e ceder 700000 reais.

Entregue repositório git com a base de recebíveis gerada conforme as condiçoes acima e o programa que resolve o problema.

Prazo de entrega: *02/10*