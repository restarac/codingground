# Cessão de Recebíveis
 Esse projeto visa resolver o problema descrito no enumciado [Problema.md](https://github.com/restarac/codingground/blob/master/cessao-recebiveis/Problema.md)
 
 Foi utilizado a versão Scala 2.12 para compilação e execução.
 
 Como IDE utilizei o coddingGround, ide online para execução e compilação do codigo scala.
 
 Basicamente ela tem 2 funções:
 - Gerar uma base de dados aletoria, que é feito pela ```RandomGenerator.scala``` 
 - Escolher o montante exato solicitado com a menor taxa de perda dentre os recebiveis que estão na base ```CessionChooser.scala```
 
# Quero executar agora!

### Siga esses passos e o ambiente estará rodando em 2 min
 - faça o download do arquivo [cessao-recebiveis.tar.gz](https://github.com/restarac/codingground/blob/master/cessao-recebiveis/cessao-recebiveis.tar.gz), ele contem todos os arquivos para upload.
 - Acesse https://www.tutorialspoint.com/compile_scala_online.php, vá no menu "Project" -> "Upload Project" selecione o arquivo baixado no passo anterior
 - Click no botão "compile" do coding-ground (qualquer alteração feita nos arquivos deverá ser recompilado.
 - Click no botão "execute" do coding-ground
 
### Quer gerar uma outra fonte de dados(generated.csv)?
 - No codingGround abra o arquivo HelloWorld.scala
 - Comente as linhas da chamada ```CessionChooser.main(args)```, se quiser faça o mesmo com o log.
 - Descomente as linhas da chamada ```RandomGenerator.main(args)```.
 - "Compile" e "Execute", done!
 
### Porque precisa dessa classe HelloWorld.scala?
 - O codingGround é staless, e o padrão da chamada compile e execute é esse nome de classe e o metodo main.
 - Caso seja removida, requer todas as vezes configurar o botão execute para outra classe (ponto negativo do codingGround).
 - Convention over configuration \o/
