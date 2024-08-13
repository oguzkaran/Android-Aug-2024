### C ve Sistem Programcıları Derneği
### Android Programlama Kursu
### Kotlin Programlama Dili
### Eğitmen: Oğuz KARAN

**_Anahtar Notlar:_** Burada ağırlıklı olarak KotlinJVM üzerinde durulacaktır.
>*Hello, World programı<br>Kotlin 1.3 versiyonundan itibaren main fonksiyonunun parametresi olmak zorunda değildir*

```kotlin
package org.csystem.app

fun main() {
    println("Hello, World")
}
```

>*Anahtar Notlar: Kotlin'de bir fonksiyon fun anahtar sözcüğü ile bildirilir. Kotlin'de bir fonksiyonun geri dönüş değeri fonksiyon isminden önce yazılmaz. Bir fonksiyonun geri dönüş değeri yoksa herhangi bir geri dönüş değeri bilgisi yazılmayabilir.*

>*Bir fonksiyonu çağıran fonksiyon (caller) ile çağrılan fonksiyon (callee) aynı pakette ise paket ismi kullanılmayabilir. Yani aynı ".kt" uzantılı dosyada bulunan veya farklı dosyada fakat aynı paket altında bulunan fonksiyonlar doğrudan çağrılabilir*

```kotlin
package org.csystem.app

fun main() {
    println("Hello, World")
    foo()
    println("Goodbye, World")
}

fun foo() {
    println("foo")
    bar()
}

fun bar() {
    println("bar")
}
```

>*Bir fonksiyonun geri dönüş değeri fonksiyon bildiriminde gövde yazılmadan önce, :'den sonra yazılmalıdır. Unit C, C++, Java ve C#'daki void anahtar sözcüğüne karşılık getirilebilir. Kotlin 1.1 versiyonundan sonra geri dönüş değeri olmayan fonksiyonlar için Unit yazılması zorunlu değildir*

```kotlin
package org.csystem.app

fun main() : Unit {
    println("Hello, World")
    foo()
    println("Goodbye, World")
}

fun foo() {
    println("foo")
    bar()
}

fun bar() : Unit {
    println("bar")
}
```

**Kotlin'de Temel Türler**

| Tür ismi | Uzunluk (byte) | Sınır Değerler |
|----------|----------------|-|
| Short    | 2              |[-32768, +32767]|
| Int      | 4              |[-2147483648, +2147483647]|
| Long     | 8              |[-9223372036854775808, +9223372036854775807]|
| Byte     | 1              |[-128, +127]|
| Float    | 4              |[±3.6 * 10-38, ±3.6 * 10+38]|
| Double   | 8              |[±1.6 * 10-308, ±1.6 * 10+308]|
| Char     | 2              |[0, 65535]|
| Boolean  | 1              |true, false|

**_Anahtar Notlar:_** Kotlin'de temel türler (primitive/built-in/pre-defined types) sınıf ile temsil edilmiştir <br>
**_Anahtar Notlar:_** Kotlin'de "işaretsiz (unsigned)" tamsayı türleri de bulunur. Bunlar ileride ele alınacaktır

>*Yerel değişken bildirimi var veya val anahtar sözcüğü ile yapılabilir. Bir değişkenin türü değişken isminden sonra `:` ile birlikte yazılır. Değişken bildirim noktasında değer verilmesi durumunda (initialization) tür yazılmayabilir.<br>Bu durumda tür atanan ifadenin türü olarak derleyici tarafından tespit edilir (type inference/deduction)*

```kotlin
package org.csystem.app

fun main() {
    var a: Int
    var b = 3.4
    val c: Char = 'a'
    var d = c

    //...
}
```

>*println fonksiyonu bir değişkenin içeriğini ekrana basar*

```kotlin
package org.csystem.app

fun main() {
    val a : Int

    a = 34

    println(a)
}
```

>*val ile bildirilen değişkenler içerisine bir kez değer verilebilir*

```kotlin
package org.csystem.app

fun main() {
    val PI : Double

    PI = 3.14

    //...

    PI = 3.15; //error
}
```
**_Anahtar Notlar:_** Kotlin'de faaliyet alanı (scope) boyunca bir kez değer verilen bir değişkenin val olarak bildirilmesi
iyi bir tekniktir

>*İki tırnak içerisindeki ifadeler (string literals), "string template" biçimindedir. Bu ifadeler içerisinde `$` ile yazılan bir ifade string oluşturulurken hesaplanır ve değeri yazı içerisinde formatlanır*

```kotlin
package org.csystem.app

fun main() {
    val a =  10
    val b = 3.4

    println("a = $a, b = $b")
    println("a = %d, %f".format(a, b)) // System.out.println(String.format("a = %d, b = %f", a, b)); ~ System.out.println("a = %d, b = %f".formatted(a, b));
}
```

>*İki tırnak içerisindeki ifadeler "string template" biçimindedir. Bu ifadeler içerisinde $ ile yazılan bir ifade string oluşturulurken hesaplanır ve değeri yazı içerisine eklenir*

```kotlin
package org.csystem.app

fun main() {
    val a = 10
    val b = 3.4
    val msg = "a = $a, b = $b"

    println(msg)
}
```

>*İki tırnak içerisindeki ifadeler "string template" biçimindedir. Bu ifadeler içerisinde $ ile yazılan bir ifade string oluşturulurken hesaplanır ve değeri yazı içerisine eklenir*

```kotlin
package org.csystem.app

fun main() {
    val a = 10
    val b = 4

    println("$a + $b = ${a + b}")
}
```

>*string template içerisinde $ karakteri formatlanmak (yani yazı içerisinde çıkartılmak) istenirse ters bölü ile(escape sequence) olarak yazılmalıdır. Eğer $ tek başına yazı içerisinde kullanılırsa doğrudan yazılabilir.*

```kotlin
package org.csystem.app

fun main() {
    val amount = 100

    println("Amount:$amount \$s")
}
```

>*Aşağıdaki örnekte en soldaki $ karakteri için ters bölü kullanılmasına gerek yoktur*

```kotlin
package org.csystem.app

fun main() {
    val amount = 100

    println("Amount:$$amount")
}
```

>*Aşağıdaki örnekte metot içerisinde bir alt blok bildirimi yapılmamıştır. Aşağıdaki örnekte yapılanın ne olduğu ileride ele alınacaktır*

```kotlin
package org.csystem.app

fun main() {
    {
        val a = 10

        println(a)
    }
}
```

>*Yukarıdaki örnekte blok olarak kullanılmak isteniyorsa run fonksiyonu aşağıdaki gibi çağrılabilir. run fonksiyonu ve aşağıdaki gibi fonksiyon çağırma detayları ileride ele alınacaktır. Örnekteki kodun Java karşılığı:*

```java
    package org.csystem.app;
    
    class App {
        public static void main(String[] args)
        {
            {
                int a = 10;
        
                System.out.println(a);
            }
        }
    }
```

```kotlin
package org.csystem.app

fun main() {
    run {
        val a = 10

        println(a)
    }
}
```

>*Yukarıdaki örnek aşağıdaki gibi de yapılabilir. Detaylar ileride ele alınacaktır*

```kotlin
package org.csystem.app

fun main() {
    {
        val a = 10

        println(a)
    }()
}
```

>*Kapsayan bloklarda aynı isimli yerel değişken bildirimi geçerlidir. Bu durumda içteki blokta o blok içerisinde bildirilen değişken kapsayan bloktaki değişken ismini maskeler (shadowing). Bu durumda birçok derleyici ve IDE uyarı verir. Aslında aşağıdaki işlemin burada ankatıldığından daha farkjlı bir detayı vardır. Bu durum ileride ele alınacaktır*

```kotlin
package org.csystem.app

fun main() {
    val a: Int

    a = 45

    run {
        println("a = $a")
        val a: Double = 23.6;

        println("a = $a")
    }

    println("a = $a")
}
```

>*Temel türler kategori olarak değer türündendir (value type). Yani temel türden değişkenler içerisinde adres tutulmaz*

```kotlin
package org.csystem.app

fun main() {
    var a = 10
    val b = a

    println("a = $a")
    println("b = $b")

    a = 45

    println("a = $a")
    println("b = $b")
}
```

>*Klavyeden (aslında stdin'den) Int türden değer okuma kalıbı. Örnekteki bazı operatörler ileride ele alınacaktır*

```kotlin
package org.csystem.app

fun main() {
    print("Bir sayı giriniz:")
    val a = readLine()!!.toInt()

    println("${a * a}")
}
```

>*Kotlin 1.6 ile birlikte klavyeden (aslında stdin'den) enter basılana kadar girilen yazıyı bir String olarak okuyan readln fonkiyonu eklenmiştir*

```kotlin
package org.csystem.app

fun main() {
    print("Bir sayı giriniz:")
    val a = readln().toInt()

    println("${a * a}")
}
```

>*Klavyeden Double türden değer okuma kalıbı*

```kotlin
package org.csystem.app

fun main() {
    print("Bir sayı giriniz:")
    val a = readln().toDouble()

    println("${a * a}")
}
```

>*Klavyeden Long türden değer okuma kalıbı*

```kotlin
package org.csystem.app

fun main() {
    print("Bir sayı giriniz:")
    val a = readln().toLong()

    println("${a * a}")
}
```

>*Klavyeden değer okunması*

```kotlin
package org.csystem.app

fun main() {
    print("Birinci sayıyı giriniz:")
    val a = readln().toDouble()

    print("İkinci sayıyı giriniz:")
    val b = readln().toDouble()

    println("$a + $b = ${a + b}")
}
```

>*Sınıf Çalışması: Klavyeden girilen iki tamsayının toplamını, çarpımını ve farkını ekrana yazdıran programı yazınız*

```kotlin
package org.csystem.app

fun main() {
    print("Birinci sayıyı giriniz:")
    val a = readln().toInt()

    print("İkinci sayıyı giriniz:")
    val b = readln().toInt()

    println("$a + $b = ${a + b}")
    println("$a * $b = ${a * b}")
    println("$a - $b = ${a - b}")

    println("Tekrar yapıyor musunuz?")
}
```

>*Kotlin'de de değer verilmemiş bir değişken kullanılamaz. Yerel değişkenlere değer verilmesi programcının sorumluluğundadır*

```kotlin
package org.csystem.app

fun main() {
    var a: Int
    var b = a * 2 //error

    println(a) //error
}
```

#### Sabitler ####

>*Sayı nokta içermiyorsa ve hiçbir sonek almamışsa Int veya Long türlerinden ilk olarak hangisinin sınırları içerisindeyse o türdendir. Sayı Long türü sınırları dışındaysa error oluşur*

```kotlin
package org.csystem.app

fun main() {
    var a : Int = 2000000000
    var b : Long = 4000000000

    //...
}
```

>*Sayı nokta içermiyorsa ve L sonekini almışsa Long türdendir. Sayı Long türü sınırları dışındaysa error oluşur. Küçük harf L kullanılması geçersizdir*

```kotlin
package org.csystem.app

fun main() {
    var b : Long = 4000000000L
    var a = 10L

    //...
}
```

>*Kotlin'de bir sabitin sonunda D veya d soneki geçersizdir*

```kotlin
package org.csystem.app

fun main() {
    val a = 3D //error

    //...
}
```

>*Sayı nokta içersin ya da içermesin F veya f sonekini almışsa Float türdendir*

```kotlin
package org.csystem.app

fun main() {
    var a : Float = 4000000000F
    var b : Float = 3.4f

    //...
}
```

>*Sayı nokta içeriyorsa ve hiçbir sonek almamışsa Double türdendir*

```kotlin
package org.csystem.app

fun main() {
    var a: Double = 3.4

    //...
}
```

>*Kotlin'de nokta içeren bir sabitin noktadan önceki kısmı (tam kısmı) sıfır ise sıfır yazılmayabilir.Ancak noktadan sonraki kısmı (ondalık kısmı) sıfır ise noktadan sonra bir şey yazmamak geçersizdir*

```kotlin
package org.csystem.app

fun main() {
    var a = .3
    var b = 4. //error
}
```

>*Kotlin' de hexadecimal sabitler 0x veya 0X ile yazılabilir*

```kotlin
package org.csystem.app

fun main() {
    val a = 0xA

    println("a = $a")
}
```

>*Kotlin'de octal sabit yoktur. Sayının başına sıfır konması da geçersizdir*

```kotlin
package org.csystem.app

fun main() {
    val a = 012 //error

    println("a = $a")
}
```

>*Kotlin'de binary sabit 0b veya 0B ile kullanılabilmektedir*

```kotlin
package org.csystem.app

fun main() {
    val a = 0b1010

    println("a = $a")
}
```

>*Kotlin' de gerçek sayı sabitleri üstel olarak yazılabilir. Sayının değeri ne olursa olsun üstel olarak yazılansabitler Double türdendir*

```kotlin
package org.csystem.app

fun main() {
    val avogadroNumber: Double = 6.02E23

    println("Avogadro Number = $avogadroNumber")
}
```

>*Kotlin'de karakter sabitleri tek tırnak ile yazılabilir*

```kotlin
package org.csystem.app

fun main() {
    val ch: Char = 'c'

    println("ch = $ch")
}
```

>*Kotlin'de tek tırnak içerisinde özel durumlar dışında birden fazla karakter yazılamaz*

```kotlin
package org.csystem.app

fun main() {
    val ch: Char = 'ab' //error

    println("ch = $ch")
}
```

>*Kotlin'de ters bölü u kullanımı*

```kotlin
package org.csystem.app

fun main() {
    val ch = '\uACB3'

    println(ch)
}
```

>*Kotlin'de desteklenene "escape sequence" karakterler. Kotlin'de bazı programlama dillerinde desteklenen escape sequence karakterler desteklenmez. Örneğin `\0` ile gösterilen null karakter Kotlin'de geçersizdir. Ancak kullanılmak istenirseaşağıdaki gibi `\u0000` biçiminde yazılabilir*

```kotlin
package org.csystem.app

fun main() {
    var ch1 = '\''
    var ch2 = '"'
    var ch3 = '\"'
    var ch4 = '\\'
    var ch5 = '$'
    var ch6 = '\$'
    var ch7 = '\t'
    var ch8 = '\n'
    var ch9 = '\r'
    var ch19 = '\u0000' //null character

    //...
}
```

>*Kotlin'de backtic '`'  karakteri ile bir anahtar sözcük tek başına değişken ismi yapılabilir.*

```kotlin
package org.csystem.app

fun main() {
    val `val` = 10

    println(`val`)
}
```

>*Anahtar Notlar: Şüphesiz bir anahtar sözcüğün doğrudan değişken ismi olarak kullanımı pratikte çok anlamlı değildir. IntelliJ gibi bazı IDE'lerin Java ile yazılmış bir kodu Kotlin ile yazılmış bir kod dönüştüren translator eklentileri vardır. Bu translator'lar dönüşümde Java'da değişken ismi olarak yapılmış ancak Kotlin'de anahtar sözcük olan isimleri backtic karakteri sarmalayarak Kotlin kod üretirler*

Kotlin'de aşağıdaki iki durum dışında değişken isimlendirme kuralları Java ile aynıdır.
1. backtic kullanımı
2. Kotlin'de alttire karakteri tek başına değişken ismi olarak kullanılamaz.

Anımsanacağı gibi Java 9 öncesinde
alttire karakteri tek başına değişken ismi olarak kullanılabilmekteydi

>*İki tane iki tırnak karakteri arasında ters bölü tek başına kullanıldığında escape sequence karakter bekler*

```kotlin
package org.csystem.app

fun main() {
    println("C:\test\numbers.dat")
}
```

>*İki tane iki tırnak karakteri arasında ters bölü karakterinin kullanımı*

```kotlin
package org.csystem.app

fun main() {
    println("C:\\test\\numbers.dat")
}
```

>*`$` karakterinin yazıda kullanımı*

```kotlin
package org.csystem.app

fun main() {
    println("\$a")
}
```

>*Sabitlerin basamakları arasında alttire karakteri istenildiği kadar kullanılabilir.*

```kotlin
package org.csystem.app

fun main() {
    val a = 4_000_000_000

    println("a = $a")
}
```

>*Sabitlerde alttire kullanımı*

```kotlin
package org.csystem.app

fun main() {
    val a = 1__________________0

    println("a = $a")
}
```

>*Sabitlerde alttire kullanımı*

```kotlin
package org.csystem.app

fun main() {
    val a = 0b0100_1000__0001_1000

    println("a = $a")
}
```

>*Kotlin'de tek bir ifade içeren fonksiyonlar (single expression functions) için gövde yazmaya gerek yoktur. Tek ifadeli fonksiyonlar için geri dönüş değeri bilgisi yazılmayabilir*

```kotlin
package org.csystem.app

fun main() {
    print("Input a number:")
    val a = readln().toInt()

    print(isEven(a))
    print(isOdd(a))
}

fun isEven(a: Int) = a % 2 == 0
fun isOdd(a: Int): Boolean = !isEven(a)
```

>*Fonksiyonların parametreleri ve argümanlar ile çağrılması*

```kotlin
package org.csystem.app

fun main() {
    display(10, 4.5)
    println(add(10, 20))
}

fun add(a: Int, b: Int) = a + b
fun display(a: Int, b: Double) = println("a = $a, b = $b")
```

>*Fonksiyonların parametre değişkenleri var veya val anahtar sözcüğü ile bildirilemez*

```kotlin
fun foo(val a: Int, var b: Double) = println("a = $a, b = $b") //error
```

>*Fonksiyonların parametre değişkenlerinin fonksiyon içerisinde değerin değiştirilmesi geçersizdir*

```kotlin
fun foo(a: Int, b: Double) {
    a *= 2 //error
    println("a = $a, b = $b")
}
```

>*Bir fonksiyon içerisinde bir parametre değişken ismi ile aynı olaran yerel değişken bildirimi geçerlidir. Ancak bazı programcılar bu durumu özellikle kullanmaz*

```kotlin
package org.csystem.app

fun main() {
    foo(10, 4.5)
}

fun foo(a: Int, b: Double) {
    var a = a
    var b = b

    a *= 2
    b -= 4
    println("a = $a, b = $b")
}
```

>*Yukarıdaki örnek aşağıdaki gibi de yapılabilir*

```kotlin
package org.csystem.app

fun main() {
    foo(10, 4.5)
}

fun foo(a: Int, b: Double) {
    var x = a
    var y = b

    x *= 2
    y -= 4
    println("a = $x, b = $y")
}
```

>*Fonksiyonların çağrılması*

```kotlin
package org.csystem.app

fun main() {
    print("Birinci sayıyı giriniz:")
    val a = readln().toInt()

    print("İkinci sayıyı giriniz:")
    val b = readln().toInt()

    println("$a + $b = ${add(a, b)}")
}

fun add(a: Int, b: Int) = a + b

```

>*Bir fonksiyonun default argümanları olabilir. Programcı default argümanları anlamlı olacak şekilde belirlemelidir. Örneğin bir fonksiyonun hiç bir parametresinin default argümanı almayabilir*

```kotlin
package org.csystem.app

fun main() {
    println(add(10, 20))
    println(add(23))
}

fun add(a:Int, b: Int = 0) = a + b
```

>*Bir fonksiyonun default argümanları olabilir. Programcı default argümanları anlamlı olacak şekilde belirlemelidir. Örneğin bir fonksiyonun hiç bir parametresinin default argümanı almayabilir*

```kotlin
package org.csystem.app

fun main() {
    println(add(10, 20))
    println(add(23))
    println(add())
}

fun add(a:Int = 0, b: Int = 0) = a + b
```

>*Bir fonksiyon çağrılırken isimli argümanlar (named argument) verilebilir*

```kotlin
package org.csystem.app

fun main() {
    display(10, 20)
    display(b = 10, a = 20)
}

fun display(a: Int, b: Int) = println("a = $a, b = $b")
```

>*Bir fonksiyon çağrılırken isimli argümanlar verilebilir*

```kotlin
package org.csystem.app

fun main() {
    foo(10, c = 's', b = 3.8)
    foo(c = 'd', b = 3.4, a = 10)
}

fun foo(a: Int, b: Double, c: Char) = println("a = $a, b = $b, c = $c")
```

>*İsimli argümanların default argüman alan fonksiyonlarda kullanımına çok sık rastalanır. Zaten çoğu zaman da isimli argüman kullanımı default argümanlar ile anlamlıdır*

```kotlin
package org.csystem.app

fun main() {
    foo(b = 3.4, c = 'd')
}

fun foo(a: Int = 67, b: Double, c: Char) = println("a = $a, b = $b, c = $c")
```

>*İsimli argümanların default argüman alan metotlarda kullanımına çok sık rastalanır. Zaten çoğu zaman da
isimli argüman kullanımı default argümanlar ile anlamlıdır*

```kotlin
package org.csystem.app

fun main() {
    foo(b = 3.4, c = 'd')
    foo(23)
    foo(c = 'Z');
}

fun foo(a: Int = 67, b: Double = 0.1, c: Char = 'C') = println("a = $a, b = $b, c = $c")
```

>*Aşağıdaki örneği inceleyiniz*

```kotlin
package org.csystem.app

fun main() {
    foo(b = 23, c = 30)
    foo(a = 23, c = 45)
    foo(34)
    foo(c = 67)
}

fun foo(a: Int = 10, b: Int = 20, c: Int = 30) = println("a = $a, b = $b, c = $c")
```

>*Aşağıdaki örneği inceleyiniz. bar metodunun parametresinin default argümanı olan add metodu çağrısı, bar default arguman ile çağrıldığında çağrılır*

```kotlin
package org.csystem.app

fun main() {
    bar()
    println("--------------------------------")
    bar(10)
}

fun add(a: Int, b: Int): Int {
    println("add");

    return a + b
}

fun bar(a: Int = add(10, 20)) = println("a = $a")
```

>*Aşağıdaki örneği inceleyiniz. ** ile belirtilen çağrıda "tam uyum (best match)" dolayısıyla parametresiz foo çağrılır. Aşağıdaki Int parametreli fonksiyon için parametresiz foo varken default argüman anlamlı mıdır? Şüphesiz bu soru örnek özelinde düşünülmeldir*

```kotlin
package org.csystem.app

fun main() {
    foo() //**
    foo(34)
    foo(0)
}

fun foo(a: Int = 0) = println("a = $a")
fun foo() = println("foo")
```

**_Anahtar Notlar:_** Kotlin'e 1.2 versiyonundan itibaren matematiksel işlemler yapan ayrı bir grup fonksiyon eklenmiştir.
Programcı bu fonksiyonları kullanmalıdır. Kotlin math kütüphanesi ileride kullanılacaktır<br>
**_Anahtar Notlar:_** Aslında Kotlin JVM programcısı, yapacağı işleme yönelik fonksiyonlar Kotlin'de standart olarak varsa
onu kullanmalıdır. Yoksa zaten JavaSE'yi kullanacaktır

>*Aşağıdaki örneği inceleyiniz*

```kotlin
package org.csystem.app

import kotlin.math.sqrt

fun main() {
    print("Input number:")
    val a = readln().toDouble()

    println("sqrt($a) = ${sqrt(a)}")
}
```

>*Kotlin'de fonksiyon içerisinde fonksiyon bildirimi yapılabilir. İçteki fonksiyon bildirildiği yerden bildirildiği bloğun sonuna kadar çağrılabilir. Başka bir yerden çağrılamaz. Bu tür fonksiyonlara "yerel (local) fonksiyonlar" denir*

```kotlin
package org.csystem.app

fun main() {
    foo()
}

fun foo() {
    fun bar() {
        //...
        println("bar")
    }
    println("foo")
    bar()
}
```

#### Yerel fonksiyonlar ####

```kotlin
package org.csystem.app

fun main() {
    print("Bir sayı giriniz:")
    val a = readln().toInt()

    displayStatus(a)
}

fun displayStatus(a: Int) {
    fun isEven(x: Int) = x % 2 == 0

    if (isEven(a))
        println("$a is even")
    else
        println("$a is odd")
}
```

>*Yerel fonksiyonlar içerisinde kendisinden önce bildirilen yerel değişkenler ve parametre değişkenleri kullanılabilir. Bu kavrama "capture" denir*

```kotlin
package org.csystem.app

fun main() {
    print("Bir sayı giriniz:")
    val a = readln().toInt()

    displayStatus(a)
}

fun displayStatus(a: Int) {
    fun isEven() = a % 2 == 0

    if (isEven())
        println("$a is even")
    else
        println("$a is odd")

    if (isEven())
        println("$a is even")
    else
        println("$a is odd")
}
```

>*Aşağıdaki örneği inceleyiniz*

```kotlin
package org.csystem.app

import kotlin.math.sqrt
import kotlin.math.pow

fun main() {
    print("Koordinatları giriniz:")
    val x1 = readln().toDouble()
    val y1 = readln().toDouble()
    val x2 = readln().toDouble()
    val y2 = readln().toDouble()

    printDistance(x1, y1, x2, y2)
}

fun distance(x1: Double, y1: Double, x2: Double, y2: Double) = sqrt((x1 - x2).pow(2) + (y1 - y2).pow(2))

fun printDistance(x1: Double, y1: Double, x2: Double, y2: Double) {
    fun distance() = sqrt((x1 - x2).pow(2) + (y1 - y2).pow(2))
    println(distance())

    //...

    println(distance(x1, y1, x2, y2))
}
```

>*Yerel fonksiyonlar içerisinde kendisinden önce bildirilen yerel fonksiyonlar da çağrılabilir*

```kotlin
package org.csystem.app

fun main() {
    print("Bir sayı giriniz:")
    val a = readln().toInt()

    foo(a)
}

fun foo(a: Int) {
    fun mod(x: Int, y: Int) = x % y
    fun isEven() = mod(a, 2) == 0

    if (isEven())
        println("$a is even")
    else
        println("$a is odd")
}
```

>*Yerel fonksiyonlar içerisinde kendisinden önce bildirilen yerel değişkenler kullanılabilir hatta değiştirilebilir.*

**_Anahtar Notlar:_** Java'da yakalanan (capture) değişkenlere faaliyet alanları boyunca bir kez değer verilebilir. Bu
değerin yakalanmadan önce yapılması zorunludur (effectively final)

```kotlin
package org.csystem.app

fun main() {
    print("Bir sayı giriniz:")
    val a = readln().toInt()

    foo(a)
}

fun foo(a: Int) {
    var temp = a
    fun isEven() = ++temp % 2 == 0

    if (isEven())
        println("$temp is even")
    else
        println("$temp is odd")

    println("temp = $temp")

    if (isEven())
        println("$temp is even")
    else
        println("$temp is odd")

    println("temp = $temp")
}
```

#### Yerel fonksiyonlar ####

```kotlin
package org.csystem.app

fun main() {
    display(20, 45)
}

fun display(min: Int, max:Int) {
    //...
    for (value in min..max) {
        fun isEven() = value % 2 == 0
        if (isEven())
            print("$value ")
    }

    println()
}
```

>*Kotlin'de her operatörün karşılık geldiği bir fonksiyon bulunmaktadır. Bu şekilde tasarım ilgili fonksiyonun olduğu türlere ilişkin değerlerin o operatörle kullanılabilmesi anlamına gelir. Yani örneğin Complex isimli bir sınıfın operator fun plus(z: Complex) parametreli bir fonksiyonu uygun şekilde yazılmışsa z1 ve z2 Complex türden referansları için<br>
`z1 + z2 -> z1.plus(z2)`<br>
biçiminde kullanılabilir.<br>Temel türlerin de uygun operatör fonksiyonları yazılıdığından işlemler yapılabilir*

>*Aritmetik `+` operatörü ve plus fonksiyonu*

```kotlin
package org.csystem.app

fun main()
{
    val a : Int = 10
    val b : Int = 3
    var sum = a + b

    println("sum = $sum")

    sum = a.plus(b)

    println("sum = $sum")
}
```

>*Aritmetik `*` operatörü ve times fonksiyonu*

```kotlin
package org.csystem.app

fun main()
{
    val a : Int = 10
    val b : Int = 3
    var result = a * b

    println("result = $result")

    result = a.times(b)

    println("result = $result")
}
```

>*Aritmetik `-` operatörü ve minus fonksiyonu*

```kotlin
package org.csystem.app

fun main()
{
    val a : Int = 10
    val b : Int = 3
    var result = a - b

    println("result = $result")

    result = a.minus(b)

    println("result = $result")
}
```

>*Aritmetik / operatörü ve div fonksiyonu*

```kotlin
package org.csystem.app

fun main()
{
    val a : Int = 10
    val b : Int = 3
    var result = a / b

    println("result = $result")

    result = a.div(b)

    println("result = $result")
}
```

>*Mod operatörünün birinci operandı negatif ise sonuç her zaman negatif çıkar.<br>Yani mod operatörünün ürettiği değerin işareti birinci operandının işareti ile aynıdır*

```kotlin
package org.csystem.app

fun main()
{
    print("Birinci sayıyı giriniz:")
    val a = readln().toInt()

    print("İkinci sayıyı giriniz:")
    val b = readln().toInt()

    println("$a % $b = ${a % b}")
}
```

>*Aritmetik `%` operatörü ve rem fonksiyonu*

```kotlin
package org.csystem.app

fun main()
{
    print("Birinci sayıyı giriniz:")
    val a = readln().toInt()

    print("İkinci sayıyı giriniz:")
    val b = readln().toInt()

    println("$a % $b = ${a.rem(b)}")
}
```

>*Anahtar Notlar: `%` operatörüne karşılık gelen fonksiyon Kotlin'in ilk sürümlerinde "mod" isimli bir fonksiyondu. Daha sonra mod fonksiyonu "rem" fonksiyonu olarak değiştirildi. Ancak Kotlin 1.5 ile birlikte bir çok eklenen matematiksel (extension olarak yazılan) fonksiyon ile birlikte Matematikteki mod işlemine karşılık "mod" isimli fonksiyon da eklendi. Bu anlamda mod fonksiyonu operatör fonksiyonu değildir*

>*mod fonksiyonu*

```kotlin
package org.csystem.app

fun main()
{
    print("Birinci sayıyı giriniz:")
    val a = readln().toInt()

    print("İkinci sayıyı giriniz:")
    val b = readln().toInt()

    println("$a % $b = ${a.mod(b)}")
}
```

>*Sınıf Çalışması: Parametresi ile aldığı 3 basamaklı Int türden bir sayının basamakları toplamını döndüren sum3Digits fonksiyonu ve test kodunu yazınız. Fonksiyon basamak sayısı kontrolü yapmayacaktır*

```kotlin
package org.csystem.app

fun main() = runSum3DigitsTest()

fun runSum3DigitsTest()
{
    print("3 basamaklı bir sayı griniz:")
    val value = readln().toInt()

    print("$value sayısının basamakları toplamı:${sum3Digits(value)}")
}

fun sum3Digits(value: Int) : Int
{
    val a = value / 100
    val b  = value / 10 % 10
    val c = value % 10

    return kotlin.math.abs(a + b + c);
}
```

>*Sınıf Çalışması: Parametresi ile aldığı 3 basamaklı Int türden bir sayının basamakları toplamını döndüren sum3Digits fonksiyonu ve test kodunu yazınız. Fonksiyon basamak sayısı kontrolü yapmayacaktır*

```kotlin
package org.csystem.app

fun main() = runSum3DigitsTest()

fun runSum3DigitsTest()
{
    print("3 basamaklı bir sayı griniz:")
    val value = readln().toInt()

    print("$value sayısının basamakları toplamı:${sum3Digits(value)}")
}

fun sum3Digits(value: Int) : Int
{
    val a = value.div(100)
    val b  = value.div(10).rem(10)
    val c = value.rem(10)

    return kotlin.math.abs(a + b + c);
}
```

>*İşaret `-` operatörü ve unaryMinus fonksiyonu*

```kotlin
package org.csystem.app

fun main()
{
    print("Bir sayı giriniz:")
    val a = readln().toInt()
    var b = -a

    println("b = $b")

    b = a.unaryMinus()

    println("b = $b")
}
```

>*İşaret `+` operatörü ve unaryPlus fonksiyonu*

```kotlin
package org.csystem.app

fun main()
{
    print("Bir sayı giriniz:")
    val a = readln().toInt()
    var b = +a

    println("b = $b")

    b = a.unaryPlus()

    println("b = $b")
}
```

>*`++` operatörünün kullanımı nasıl olursa olsun değişkeni bir artırır*

```kotlin
package org.csystem.app

fun main()
{
    var a = 10

    a++
    print("a = $a")
}
```

>*`++` operatörünün kullanımı nasıl olursa olsun değişkeni bir artırır*

```kotlin
package org.csystem.app

fun main()
{
    var a = 10

    ++a
    print("a = $a")
}
```

>*`++` operatörünün prefix kullanımı*

```kotlin
package org.csystem.app

fun main()
{
    var a = 10
    val b = ++a

    println("a = $a")
    println("b = $b")
}
```

>*`++` operatörünün postfix kullanımı*

```kotlin
package org.csystem.app

fun main()
{
    var a = 10
    val b = a++

    println("a = $a")
    println("b = $b")
}
```

>*`++` operatörünün fonksiyon karşılığı. inc fonksiyonu artırılmış değeri döndürür. Temel türlere ilişkin sınıflar immutable olduğundan inc fonksiyonu artırma işlemini yapmaz. Artırılmış değere geri döner*

```kotlin
package org.csystem.app

fun main()
{
    val a : Int = 10
    val b = a.inc() //val b = a + 1

    println("a = $a")
    println("b = $b")
}
```

>*`++` operatörünün fonksiyon karşılığı*

```kotlin
package org.csystem.app

fun main()
{
    var a = 10

    a = a.inc() //a = a + 1
    println("a = $a")
}
```

>*`--` operatörünün fonksiyon karşılığı. dec fonksiyonu azaltılmış değeri döndürür. Temel türlere ilişkin sınıflar immutable olduğundan dec fonksiyonu artırma işlemini yapmaz. Artırılmış değere geri döner*

```kotlin
package org.csystem.app

fun main()
{
    val a = 10
    val b = a.dec() //b = a - 1

    println("a = $a")
    println("b = $b")
}
```

>*`--` operatörünün fonksiyon karşılığı*

```kotlin
package org.csystem.app

fun main()
{
    var a = 10

    a = a.dec() //a = a - 1
    println("a = $a")
}
```

>`*==` ve `!=` operatörleri. Bu operatörlerin fonksiyon karşılıkları ileride ele alınacaktır*

```kotlin
package org.csystem.app

fun main()
{
    print("Birinci sayıyı giriniz:")
    val a = readln().toInt()

    print("İkinci sayıyı giriniz:")
    val b = readln().toInt()

    val resEquals = a == b
    val resNotEquals = a != b

    println("resEquals = $resEquals, resNotEquals = $resNotEquals")
}
```

>`*>`, `<=`, `<`, `>=` operatörlerinin fonksiyon karşılıkları*

```kotlin
package org.csystem.app

fun main() {
    print("Birinci sayıyı giriniz:")
    val a = readln().toInt()

    print("İkinci sayıyı giriniz:")
    val b = readln().toInt()

    println("$a > $b -> ${a > b}")
    println("$a > $b -> ${a.compareTo(b) > 0}")
    println("$a <= $b -> ${a <= b}")
    println("$a <= $b -> ${a.compareTo(b) <= 0}")
    println("$a < $b -> ${a < b}")
    println("$a < $b -> ${a.compareTo(b) < 0}")
    println("$a >= $b -> ${a >= b}")
    println("$a >= $b -> ${a.compareTo(b) >= 0}")
}
```

>*Mantıksal operatörlerin kısa devre davranışı (short circuit behavior)*

```kotlin
package org.csystem.app

fun main()
{
    val result = foo() || bar()

    println("result = $result")
}

fun foo() : Boolean
{
    println("foo")

    return true
}

fun bar() : Boolean
{
    println("bar")

    return false
}
```

>*Mantıksal operatörlerin kısa devre davranışı (short circuit behavior)*

```kotlin
package org.csystem.app

fun main()
{
    val result = bar() && foo()

    println("result = $result")
}

fun foo() : Boolean
{
    println("foo")

    return true
}

fun bar() : Boolean
{
    println("bar")

    return false
}
```

>*Mantıksal operatörlerin fonksiyon karşılıkları. Aşağıdaki örnekte bar fonksiyonu çağrılmadan or fonksiyonunun çağrılamayacağına dikkat ediniz. Bu durumda kısa devre davranışından faydalanılmaz*

```kotlin
package org.csystem.app

fun main()
{
    val result = foo().or(bar())

    println("result = $result")
}

fun foo() : Boolean
{
    println("foo")

    return true
}

fun bar() : Boolean
{
    println("bar")

    return false
}
```

>*`&&` ve `||` operatörleri klasik öncelik kuralına uymazlar. Bu operatörlerin aynı ifadede olması durumunda her zaman işlem soldan başlar, ancak öncelik kuralına uyulsa çıkacak sonucun aynısına ulaşılır. Yani bu operatörlerin temel amacı doğru sonuca en kısa yoldan ulaşmaktır. Şüphesiz bu kısa devre davranışı ile sağlanır*

```kotlin
package org.csystem.app

fun main()
{
    val result = foo() || bar() && tar()

    println("result = $result")
}

fun foo() : Boolean
{
    println("foo")

    return true
}

fun bar() : Boolean
{
    println("bar")

    return false
}

fun tar() : Boolean
{
    println("tar")

    return false
}
```

>*Aşağıdaki örnekte yine en kısa yoldan doğru sonuca ulaşılacak şekilde derleyici tarafından kod üretilir. Yine işlem soldan başlar ancak örnekte işlem sırasıyla operatör öncelik sırası aynıdır*

```kotlin
package org.csystem.app

fun main()
{
    val result = bar() && tar() || foo()

    println("result = $result")
}

fun foo() : Boolean
{
    println("foo")

    return true
}

fun bar() : Boolean
{
    println("bar")

    return false
}

fun tar() : Boolean
{
    println("tar")

    return false
}
```

>*Mantıksal operatörlerin kısa devre davranışı göreceli olarak programın hızını artırır*

```kotlin
package org.csystem.app

fun main()
{
    run()
}

fun run()
{
    print("a kenarını giriniz:")
    val a = readln().toDouble()

    print("b kenarını giriniz:")
    val b = readln().toDouble()

    print("c kenarını giriniz:")
    val c = readln().toDouble()

    if (isTriangle(a, b, c))
        println("$a, $b, $c bir üçgen oluşturur")
    else
        println("$a, $b, $c bir üçgen oluşturamaz")
}

fun isTriangle(a: Double, b: Double, c: Double)
        = a + b > c  && a + c > b && b + c > a && Math.abs(a - b) < c && Math.abs(a - c) < b && Math.abs(b - c) < a
```

>*Mantıksal NOT operatörü ve fonksiyon karşılığı*

```kotlin
package org.csystem.app

fun main()
{
    print("Input a flag value as true or false:")
    var flag = readln().toBoolean()
    println("flag = $flag")

    flag = !flag

    println("flag = $flag")

    println("flag = ${flag.not()}")
}
```

>*Atama operatörü*

```kotlin
package org.csystem.app

fun main()
{
    print("Bir sayı giriniz:")
    val a = readln().toInt()
    val b: Int

    b = a

    println("a = $a")
    println("b = $b")
}
```

>*Atama operatörü Kotlin'de bir ifade biçiminde ele alınmaz. Dolayısıyla aşağıdaki işlem geçersizdir. Atama operatörü bir değer üretmez*

```kotlin
package org.csystem.app

fun main()
{
    var a: Int
    var b: Int
    val c: Int = 10

    a = b = c //error
}
```

>*İşlemli atama operatörleri (augmented/compound assignment operators)*

```kotlin
package org.csystem.app

fun main()
{
    var a: Int = 10
    val b : Int = 20

    a += b

    println("a=$a")
    println("b=$b")
}
```

>*İşlemli atama operatörleri işlem önceliğinden dolayı daha yalın kodların yazılmasını sağlayabilir*

```kotlin
package org.csystem.app

fun main()
{
    var a = 10
    val b = 13

    a *= b + 1 //a = a * (b + 1)

    println("a = $a")
}
```

>*Kotlin'de bir bir sonraki satıra geçmek ve noktalı virgül (;) sonlandırıcı (terminator) olarak kullanılabilir. Bir sonraki satıra geçmek sonlandırıcı olarak kullanılıyorsa noktalı virgül konması önerilmez*

```kotlin
package org.csystem.app

fun main()
{
    var a = 10; var b = 10
    a *= b; b++ //Burada a *= b nin sonuna yazılan noktalı virgül gereklidir

    println("a = $a")
    println("b = $b")
}
```

>*if ifadesel deyiminin (expression statement), deyim olarak kullanımı*

```kotlin
package org.csystem.app

fun main()
{
    print("Bir sayı giriniz:")
    val a = readln().toInt()

    if (a % 2 == 0)
        println("Çift")
    else
        println("Tek")
}
```

>*if ifadesinin koşul operatörü yerine kullanımı*

**_Anahtar Notlar:_** Kotlin'de koşul operatörü (conditional operator) yoktur.

```kotlin
package org.csystem.app

fun main()
{
    print("Bir sayı giriniz:")
    val a = readln().toInt()

    println(if (a % 2 == 0) "Çift" else "Tek")
}
```

>*Sınıf Çalışması: Klavyeden katsayıları girilen ikinci dereceden bir denklemin köklerini bulan programı yazınız.<br>Açıklama: Programda Math sınıfının sqrt metodu kullanılacaktır*

```kotlin
package org.csystem.app

fun main() = runApp()

fun runApp()
{
    print("a?")
    val a = readln().toDouble()

    print("b?")
    val b = readln().toDouble()

    print("c?")
    val c = readln().toDouble()

    println(findRoots(a, b, c))
}

fun calculateDelta(a: Double, b: Double, c: Double) = b * b - 4 * a * c

fun findRoots(a: Double, b: Double, c: Double) : String
{
    val delta = calculateDelta(a, b, c)

    fun calculateRoots() : String
    {
        val sqrtDelta = Math.sqrt(delta)
        return "x1 = ${(-b + sqrtDelta) / (2 * a)}, x2 = ${(-b - sqrtDelta) / (2 * a)}"
    }

    return if (delta > 0)
                calculateRoots()
            else if (delta == 0.0)
                "x1 = x2 = ${-b / (2 * a)}"
            else
                "No real root"
}
```

>*Aşağıdaki örnekte else içteki if deyimine ilişkindir (dangling else)*

```kotlin
package org.csystem.app

fun main()
{
    print("a?")
    val a = readln().toInt()

    if (a % 2 == 0)
        if (a > 0)
            println("Çift pozitif")
    else
        println("Çift sayı değil")
}
```

>*Yukarıdaki problem bileşik deyim (blok) konarak çözülebilir*

```kotlin
package org.csystem.app

fun main()
{
    print("a?")
    val a = readln().toInt()

    if (a % 2 == 0) {
        if (a > 0)
            println("Çift pozitif")
    }
    else
        println("Çift sayı değil")
}
```

>*Aşağıdaki durumda dangling else yoktur*

```kotlin
package org.csystem.app

fun main()
{
    print("a?")
    val a = readln().toInt()

    if (a % 2 == 0)
        if (a > 0)
            println("Çift pozitif")
        else
            println("Çift pozitif değil")
    else
        println("Çift sayı değil")
}
```

>*Aşağıdaki örnekte if ifadesel deyimi ifade biçiminde koşul operatörü gibi kullanılmıştır. Kotlin'de koşul opratörü yoktur*

```kotlin
package org.csystem.app

fun main()
{
    print("Birinci sayıyı giriniz:")
    val a = readln().toInt()

    print("İkinci sayıyı giriniz:")
    val b = readln().toInt()

    println("max($a, $b) = ${max(a, b)}")
}

fun max(a: Int, b: Int) = if (a > b) a else b
```

>*Kotlin programlama dilinde döngüler 2(iki) gruba ayrılır:*

1. while döngü deyimleri
- Kontrolün başta yapıldığı while döngü deyimi (while döngüsü)
- Kontrolün sonda yapıldığı while döngü deyimi (do-while döngüsü)
2. for döngü deyimi

**_Anahtar Notlar:_** Buradaki for döngü deyimi Java'daki klasik for döngüsü değildir. Java'daki for-each döngü deyiminin
daha yetenekli bir biçimi olarak düşünülebilir. Kotlin'de klasik for döngüsü yoktur. Ancak klasik for döngüsünün
olmaması bir eksiklik değildir. Kotlin'deki for döngü deyimiyle çeşitli operatörler ve infix fonksiyonlar yardımıyla
klasik for döngü deyimi ihtiyacı karşılanabilmektedir. İleride detaylı olarak ele alınacaktır*

>*Kontrolün başta yapıldığı while döngüleri (while döngüsü)*

```kotlin
package org.csystem.app

fun main()
{
    print("Bir sayı giriniz:")
    val n = readln().toInt()
    var i = 0

    while (i < n) {
        print("$i ")
        ++i
    }

    println()
}
```

>*while döngü deyiminin parantezi içerisinde atama yapılması geçersizdir. Anımsanacağı gibi atama operatörü bir ifade oluşturmaz. Dolayısıyla değer üretmez*

```kotlin
package org.csystem.app

fun main()
{
    var value : Int
    var count = 0

    while ((value = readln().toInt()) != 0) //error
        ++count

    println("count=$count")
}
```

>*while döngü deyimi ile n-kez dönen döngü kalıbı*

```kotlin
package org.csystem.app

fun main()
{
    print("Bir sayı giriniz:")
    var n = readln().toInt()

    while (n-- > 0)
        print("$n ")

    println()
    println("Döngü sonrası n = $n")
}
```

>*while döngü deyimi ile n-kez dönen döngü kalıbı (dizi uyumlu)*

```kotlin
package org.csystem.app

fun main()
{
    print("Bir sayı giriniz:")
    val n = readln().toInt()
    var i = 0

    while (i < n) {
        print("$i ")
        ++i
    }

    println()
}
```

>*while döngü deyimi ile n-kez dönen döngü kalıbı (dizi uyumlu)*

```kotlin
package org.csystem.app

fun main()
{
    print("Bir sayı giriniz:")
    val n = readln().toInt()
    var i = n - 1

    while (i >= 0) {
        print("$i ")
        --i
    }

    println()
}
```

>*Sınıf Çalışması Klavyeden sıfır girilene kadar alınan sayıların toplamını bulan programı yazınız. Örnekte break deyimini kullanabilirsiniz*

**_Anahtar Notlar:_** Kotlin'de break deyiminin etiketsiz kullanımı Java ile aynıdır

```kotlin
package org.csystem.app

fun main() = runApplication()

fun runApplication()
{
    var sum = 0

    while (true) {
        print("Bir sayı giriniz:")
        val value = readln().toInt()

        if (value == 0)
            break

        sum += value
    }

    println("Toplam = $sum")
}
```

>*do-while döngü deyimi*

```kotlin
package org.csystem.app

fun main()
{
    print("Bir sayı giriniz:")
    val n = readln().toInt()
    var i = 0

    do {
        println("i = $i")
        ++i
    } while(i < n)

    println("Tekrar yapıyor musunuz?")
}
```

>*do-while döngü deyiminde Java ve C#'dan farklı olarak do-while döngü deyimi içerisinde bildirilmiş olan bir değişkenin while parantezi içerisinde kullanımı geçerlidir*

```kotlin
package org.csystem.app

fun main()
{
    do {
        val a = readln().toInt()

        println("a = $a")
    } while (a != 0)

    println("Tekrar yapıyor musunuz?")
}
```

>*Sınıf Çalışması: Parametresi ile aldığı Int türden bir sayının basamak sayısını döndüren countDigits isimli fonksiyonu döngü kullanarak yazınız yazınız ve aşağıdaki kod ile test ediniz*

```kotlin
package org.csystem.app

fun main() = runCountDigitsTest()

fun runCountDigitsTest()
{
    while (true) {
        print("Bir sayı giriniz:")
        val value = readln().toInt()

        println("$value sayısının basamak sayısı:${countDigits(value)}")

        if (value == 0)
            break
    }

    println("Tekrar yapıyor musunuz?")
}

fun countDigits(value: Int) : Int
{
    var count = 0
    var temp = value

    do {
        ++count
        temp /= 10
    } while (temp != 0)

    return count
}
```

>*Sınıf Çalışması: Parametresi ile aldığı Int türden bir sayının basamakları toplamını döndüren sumDigits fonksiyonunu yazınız ve aşağıdaki kod ile test ediniz*

```kotlin
package org.csystem.app

fun main() = runSumDigitsTest()

fun runSumDigitsTest()
{
    while (true) {
        print("Bir sayı giriniz:")
        val value = readln().toInt()

        println("$value sayısının basamakları toplamı:${sumDigits(value)}")

        if (value == 0)
            break
    }

    println("Tekrar yapıyor musunuz?")
}

fun sumDigits(value: Int) : Int
{
    var total = 0
    var temp = value

    while (temp != 0) {
        total += temp % 10
        temp /= 10;
    }

    return kotlin.math.abs(total)
}
```

>*Sınıf Çalışması: Parametresi ile aldığı Int türden bir sayının tersini döndüren reversed isimli fonksiyonu yazınız ve aşağıdaki kod ile test ediniz.<br>**_Algoritma:_** 123 -> 3 -> 3 * 10 + 2 = 32 -> 32 * 10 + 1 = 321*

```kotlin
package org.csystem.app

fun main() = runReversedTest()

fun runReversedTest()
{
    while (true) {
        print("Bir sayı giriniz:")
        val value = readln().toInt()

        println("$value sayısının tersi:${reversed(value)}")

        if (value == 0)
            break
    }

    println("Tekrar yapıyor musunuz?")
}

fun reversed(value: Int) : Int
{
    var result = 0
    var temp = value

    while (temp != 0) {
        result = result * 10 + temp % 10
        temp /= 10
    }

    return result
}
```

>*Sınıf Çalışması: Parametresi ile aldığı Int türden bir sayının palindrom olup olmadığını test eden isPalindrome fonksiyonunu yazınız ve aşağıdaki kod ile test ediniz*

```kotlin
package org.csystem.app

fun main() = runIsPalindromeTest()

fun runIsPalindromeTest()
{
    while (true) {
        print("Bir sayı giriniz:")
        val value = readln().toInt()

        println(if (isPalindrome(value)) "$value palindromdur" else "$value palindrom değildir")

        if (value == 0)
            break
    }

    println("Tekrar yapıyor musunuz?")
}

fun isPalindrome(value: Int) = reversed(value) == value

fun reversed(value: Int) : Int
{
    var result = 0
    var temp = value

    while (temp != 0) {
        result = result * 10 + temp % 10
        temp /= 10
    }

    return result
}
```

>*Aşağıdaki örnekte, Java'daki for döngü deyiminin karşılığı olan deyim yazılmıştır:*

**_Anahtar Notlar:_** `..` ile belirtilen operatör "range" sınıfları türünden referans üretir. Bu sınıflar Iterable arayüzünü desteklediği için for döngüsü ile dolaşılabilir. Iterable arayüzü ileride ele alınacaktır. Aslında Kotlin'deki for döngü deyimi Java'nın "for-each/enhanced for loop"'udur

```kotlin
package org.csystem.app

fun main()
{
    for (i in 1..10) //[1, 10]
        print("$i ")

    println()
}
```

>*for döngü deyimi*

```kotlin
package org.csystem.app

fun main()
{
    print("Bir sayı giriniz:")
    val n = readln().toInt()

    for (i in 1..n) //[1, n]
        print("$i ")

    println()
}
```

>*for döngü deyimi*

```kotlin
package org.csystem.app

fun main()
{
    print("min değerini giriniz:")
    val min = readln().toInt()

    print("max değerini giriniz:")
    val max = readln().toInt()

    for (i in min..max)
        print("$i ")

    println()
}
```

>*Dikkat aşağıdaki örnek azalarak ilerlemez. Dolayısıyla tersten dolaşmak anlamına gelmez*

```kotlin
package org.csystem.app

fun main()
{
    for (i in 10..1)
        print("$i ")

    println()
}
```

>*Aşağıdaki örnek ikişer ikişer artarak ilerleyen bir for döngüsü gibi düşünülebilir. Bu döngü teknik olarak IntProgression sınıfı türünün infix step fonksiyonu kullanılarak yapılmıştır*

```kotlin
package org.csystem.app

fun main()
{
    print("Bir sayıc giriniz:")
    val n = readln().toInt()

    for (i in 1..n step 2)
        print("$i ")

    println()
}
```

>*Sınıf Çalışması: Parametresi ile aldığı a ve b Int türden değerleri için a nın b-inci kuvvetini döndüren pow fonksiyonunu yazınız ve aşağıdaki kod ile test ediniz<br>Açıklama: Math sınıfının pow metodu veya Kotlin kütüphanesindeki pow fonksiyonu kullanılmayacaktır*

```kotlin
package org.csystem.app

fun main() = runPowTest()

fun runPowTest()
{
     while (true) {
        print("Tabanı giriniz:")
        val a = readln().toInt()

        if (a == 0)
            break;

        print("Üssü giriniz:")
        val b = readln().toInt()

        println("pow($a, $b) = ${pow(a, b)}")
    }

    println("Tekrar yapıyor musunuz?")
}

fun pow(a: Int, b: Int) : Int
{
    var result = 1
    var p = b

    while (p-- > 0)
        result *= a

    return result
}
```

>*Sınıf Çalışması: Parametresi ile aldığı a ve b Int türden değerleri için a nın b-inci kuvvetini döndüren pow fonksiyonunu yazınız ve aşağıdaki kod ile test ediniz<br>Açıklama: Math sınıfının pow metodu veya Kotlin kütüphanesindeki pow fonksiyonu kullanılmayacaktır*

```kotlin
package org.csystem.app

fun main() = runPowTest()

fun runPowTest()
{
    while (true) {
        print("Tabanı giriniz:")
        val a = readln().toInt()

        if (a == 0)
            break;

        print("Üssü giriniz:")
        val b = readln().toInt()

        println("pow($a, $b) = ${pow(a, b)}")
    }

    println("Tekrar yapıyor musunuz?")
}

fun pow(a: Int, b: Int) : Int
{
    var result = 1

    for (i in 1..b)
        result *= a

    return result
}
```

>*Sınıf Çalışması: Parametresi ile aldığı bir sayının basamaklarının basamak sayıncı kuvvetleri toplamının kendisine eşit olup olmadığını test eden isArmstrong isimli fonksiyonu yazınız ve aşağıdaki kod ile test ediniz. Fonksiyon negatif değerler için false değerini döndürecektir<br>Açıklama: Kuvvet alma işlemi için bir önceki örnekte yazılan pow fonksiyonu kullanılacaktır*

```kotlin
package org.csystem.app

fun main() = runIsArmstrongTest()

fun runIsArmstrongTest()
{
    for (value in -10..99999)
        if (isArmstrong(value))
            println(value)

    println("Tekrar yapıyor musunuz?")
}

fun isArmstrong(value: Int) = value >= 0 && getDigitsPowSum(value) == value

fun getDigitsPowSum(value: Int) : Int
{
    var temp = value
    val digitCount = countDigits(value)
    var total = 0

    while (temp != 0) {
        total += pow(temp % 10, digitCount)
        temp /= 10
    }

    return total
}

fun countDigits(value: Int) : Int
{
    var count = 0
    var temp = value

    do {
        ++count
        temp /= 10
    } while (temp != 0)

    return count
}

fun pow(a: Int, b: Int) : Int
{
    var result = 1

    for (i in 1..b)
        result *= a

    return result
}
```

>*Sınıf Çalışması: Klavyeden sıfır girilene kadar alınan sayılardan pozitif ve negatif olanlarının ayrı ayrı toplamını ve kaçar tane olduklarını bulan programı yazınız<br>**_Not:_** Örnek Kotlin'de şu ana kadar görülen konular kullanılarak yazılmıştır*

```kotlin
package org.csystem.app

fun main() = runPosNegCountApp()

fun runPosNegCountApp()
{
    var posCount = 0
    var negCount = 0

    while (true) {
        print("Bir sayı giriniz:")
        val value = readln().toInt()

        if (value == 0)
            break

        if (value > 0)
            ++posCount
        else
            ++negCount
    }

    printResult(posCount, negCount)
}

fun printResult(posCount: Int, negCount: Int)
{
    if (posCount > 0)
        println("$posCount adet pozitif sayı girdiniz")
    else
        println("Hiç pozitif sayı girmediniz")

    if (negCount > 0)
        println("$negCount adet negatif sayı girdiniz")
    else
        println("Hiç negatif sayı girmediniz")
}
```

>*Bir sayının asal olup olmadığını test eden isPrime fonksiyonu ve test kodu<br>(Yavaş versiyon)*

```kotlin
package org.csystem.app

fun main() = runIsPrimeTest()

fun runIsPrimeTest()
{
    for (n in 1..100)
        if (isPrime(n))
            print("$n ")
    println()
    println(if (isPrime(1_000_003)) "Asal" else "Asal değil")
}

fun isPrime(value: Int) : Boolean
{
    if (value <= 1)
        return false

    for (n in 2..value / 2)
        if (value % n == 0)
            return false

    return true
}
```

>*Bir sayının asal olup olmadığını test eden isPrime fonksiyonu ve test kodu<br>(Hızlı versiyon)<br>**_Kural:_** Bir sayının asal olması için karekökünden daha küçük olan asal sayıların hiçbirisine bölünememesi gerekir*

```kotlin
package org.csystem.app

fun main() = runIsPrimeTest()

fun runIsPrimeTest()
{
    println(if (isPrime(710584055392819667)) "Asal" else "Asal değil")
}

fun isPrime(value: Long) : Boolean
{
    if (value <= 1)
        return false

    if (value % 2 == 0L)
        return value == 2L

    if (value % 3 == 0L)
        return value == 3L

    if (value % 5 == 0L)
        return value == 5L

    if (value % 7 == 0L)
        return value == 7L

    var i = 11L

    while (i * i <= value) {
        if (value % i == 0L)
            return false
        i += 2
    }

    return true
}
```

>*Yukarıdaki iki fonksiyonun basit bir karşılaştırması*

```kotlin
package org.csystem.app

fun main()
{
    println(if (isPrime(1_000_003)) "Asal" else "Asal değil")
    println(if (isPrimeSlow(1_000_003)) "Asal" else "Asal değil")
}

fun isPrimeSlow(a: Int) : Boolean
{
    if (a <= 1)
        return false

    val halfValue = a / 2

    var count = 0

    for (i in 2..halfValue) {
        ++count
        if (a % i == 0)
            return false
    }

    println("isPrimeSlow:count=$count")
    return true
}

fun isPrime(a: Long) : Boolean
{
    if (a <= 1)
        return false

    if (a % 2 == 0L)
        return a == 2L

    if (a % 3 == 0L)
        return a == 3L

    if (a % 5 == 0L)
        return a == 5L

    if (a % 7 == 0L)
        return a == 7L

    var i = 11L

    var count = 0L

    while (i * i <= a) {
        ++count
        if (a % i == 0L)
            return false

        i += 2
    }

    println("isPrime:count = $count")

    return true
}
```

>*Yukarıdaki isPrime fonksiyonlarını, özellikle hızlı versiyonunu aşağıdaki sayılar ile test ediniz ve sonuçları gözlemleyiniz:*

    6750161072220585911
    1603318868174368979
    6584583408148485263
    6245098347044246839
    6285871677077738093
    5697859706174583067
    710584055392819667
    4935060337471977161
    3728803592870153407
    4331452335614730577
    1386437196678024971
    1677990107453991593
    4765603950744460867
    4498306523077899307
    4434895834573449257

>*downTo infix fonksiyonu ile for döngüsü içerisinde ters sırada dönen bir döngü yazılabilir*

```kotlin
package org.csystem.app

fun main()
{
    print("a:")
    val a = readln().toInt()

    print("b:")
    val b = readln().toInt()

    for (i in b downTo a)
        print("$i ")

    println()
}
```

>*Aşağıdaki örnekte downTo ve step fonksiyonları birlikte kullanılmıştır*

```kotlin
package org.csystem.app

fun main()
{
    print("a:")
    val a = readln().toInt()

    print("b:")
    val b = readln().toInt()

    for (i in b downTo a step 3)
        print("$i ")

    println()
}
```

>*until infix fonksiyonu ile [a, b) aralığında döngü deyimi oluşturulabilir*

```kotlin
package org.csystem.app

fun main()
{
    print("min:")
    val min = readln().toInt()
    print("bound:")
    val bound = readln().toInt()

    for (i in min until bound) //[min, bound)
        print("$i ")

    println()
}
```

>*until infix fonksiyonu ile step fonksiyonunun kullanımı*

```kotlin
package org.csystem.app

fun main()
{
    print("min:")
    val min = readln().toInt()
    print("bound:")
    val bound = readln().toInt()

    for (i in min until bound step 2) //[min, bound)
        print("$i ")

    println()
}
```

>*Aşağıdaki örnekte exception oluşur. step değeri pozitif bir tamsayı değeri olmalıdır. Buradaki örnek geriye doğru dolaşmak anlamına gelmez*

```kotlin
package org.csystem.app

fun main()
{
    val a = 10
    val b = 1

    for (i in a..b step -1)
        print("$i ")

    println()
}
```

>*Sınıf Çalışması: Parametresi ile aldığı Int türden bir n sayısı için n-inci asal sayıyı döndüren getPrime isimli fonksiyonu yazınız ve aşağıdaki kod ile test ediniz. Fonksiyon n'nin pozitif olmayan değerleri için kontrol yapmayacaktır*

```kotlin
package org.csystem.app

fun main() = runGetPrimeTest()

fun runGetPrimeTest()
{
    while (true) {
        print("Bir sayı giriniz:")
        val n = readln().toInt()

        if (n <= 0)
            break

        println("${n}. asal sayı: ${getPrime(n)}")
    }

    println("Tekrar yapıyor musunuz?")
}

fun getPrime(n: Int) : Long
{
    var count = 0
    var value = 2L

    while (true) {
        if (isPrime(value))
            ++count

        if (count == n)
            return value

        ++value
    }
}

fun isPrime(value: Long) : Boolean
{
    if (value <= 1)
        return false

    if (value % 2 == 0L)
        return value == 2L

    if (value % 3 == 0L)
        return value == 3L

    if (value % 5 == 0L)
        return value == 5L

    if (value % 7 == 0L)
        return value == 7L

    var i = 11L

    while (i * i <= value) {
        if (value % i == 0L)
            return false
        i += 2
    }

    return true
}
```

>*Etiketli break (labeled break) kullanımı. Dikkat `break@<etiket ismi>` deyimi boşluk içeremez.<br>Etiket bildirimi sonunda `@` atomu yazılmalıdır. Aşağıdaki içiçe döngünün Java'da yazılmış bir biçimi şu şekildedir*

```java
      EXIT_LOOP:
      for (int i = 10; i <= 20; ++i) {
      for (int k = 2; k <= 34; ++k) {
      System.out.printf("(%d, %d)%n", i, k);

            if ((i + k) % 11 == 0)
                break EXIT_LOOP;
        }
    }
```

>*Örnek konuyu anlatmak için yazılmıştır*

```kotlin
package org.csystem.app

fun main()
{
    EXIT_LOOP@
    for (i in 10..20) {
        for (k in 2..34) {
            println("($i, $k)")
            if ((i + k) % 11 == 0)
                break@EXIT_LOOP
        }
    }

    println("Tekrar yapıyor musunuz?")
}
```

>*Etiketli break (labeled break) kullanımı<br>Örnek konuyu anlatmak için yazılmıştır*

```kotlin
package org.csystem.app

fun main()
{
    EXIT_FIRST_LOOP@
    for (i in 10..30) {
        EXIT_SECOND_LOOP@
        for (j in 45..89) {
            for (k in 2..34) {
                println("($i, $j, $k)")

                if ((i + j + k) % 11 == 0)
                    break@EXIT_SECOND_LOOP

                if ((i + j + k) % 13 == 0)
                    break@EXIT_FIRST_LOOP
            }
        }
    }

    println("Tekrar yapıyor musunuz?")
}
```

>*Sınıf Çalışması: Klavyeden alınan a ve b Int türden değerleri için [a, b] aralığında tek ve çift sayıları ayrı ayrı toplayan programı yazınız. Örnekte isEven fonksiyonun "capture" yaptığına dikkat ediniz*

```kotlin
package org.csystem.app

fun main() = runApplication()

fun runApplication()
{
    print("a?")
    val a = readln().toInt()

    print("b?")
    val b = readln().toInt()

    findTotals(a, b)
}

fun findTotals(a: Int, b: Int)
{
    var evenTotal = 0
    var oddTotal = 0

    for (n in a..b) {
        fun isEven() = n % 2 == 0

        if (isEven())
            evenTotal += n
        else
            oddTotal += n
    }

    print("Çift sayıların toplamı:$evenTotal")
    print("Tek sayıların toplamı:$oddTotal")
}
```

>*Kotlin'de switch deyimi yoktur. Ancak benzer şekilde kullanılabilecek when ifadesi (ifadesel deyimi) vardır. Java 14 ile birlikte switch'in ifade olarak kullanımı da Java'ya eklenmiştir (switch expression). Bu anlamda switch de artık ifadesel deyim olarak kullanılabilir. switch expression, when expression'a oldukça benzemektedir*

>*when ifadesinin switch deyimine benzer kullanımı. when ifadesinde aşağı düşme (fall through) özelliği yoktur. Aşağıdaki when ifadesinin switch deyimi karşılığı şu şekildedir:*

``` java
switch (a) {
    case 1:
        System.out.println("Bir");
        break;
    case 3:
    case 5:
    case 6:
        System.out.println("3, 5 veya 6");
        break;
    default:
        System.out.println("Geçersiz değer!...");
}
```

```kotlin
package org.csystem.app

fun main()
{
    print("Bir sayı giriniz:")
    val a = readln().toInt()

    when (a) {
        1 -> println("Bir")
        3, 5, 6 -> println("3, 5 veya 6")
        else -> println("Geçersiz değer!...")
    }
}
```

>*when ifadesi*

```kotlin
package org.csystem.app

fun main()
{
    print("Telefon kodunu giriniz:")
    val code = readln().toInt()

    when (code) {
        212, 216 -> println("İstanbul")
        312 -> println("Ankara")
        372 -> println("Zonguldak")
        else -> println("Geçersiz telefon kodu!...")
    }
}
```

>*when ifadesinin parantezsiz kullanımı. Bu kullanımda koşulların -> atomundan önce yazıldığına dikkat ediniz. Java'da switch bu şekilde kullanılamaz*

```kotlin
package org.csystem.app

fun main()
{
    print("Bir sayı giriniz:")
    val a = readln().toInt()

    when {
        a > 0 -> println("Pozitif")
        a == 0 -> println("Sıfır")
        else -> println("Negatif")
    }
}
```

>*when ifadesinin ürettiği değerin kullanılması*

```kotlin
package org.csystem.app

fun main()
{
    print("Bir sayı giriniz:")
    val a = readln().toInt()

    val message = when {
        a > 0 -> "Pozitif"
        a == 0 -> "Sıfır"
        else -> "Negatif"
    }

    println(message)
}
```

>*when ifadesinin ürettiği değerin kullanılması*

```kotlin
package org.csystem.app

fun main()
{
    print("Bir sayı giriniz:")
    val a = readln().toInt()

    println(when {
        a > 0 -> "Pozitif"
        a == 0 -> "Sıfır"
        else -> "Negatif"
    })
}
```

>*Sınıf Çalışması: Klavyeden katsayıları girilen ikinci dereceden bir denklemin köklerini bulan programı yazınız.*

Açıklamalar:

- if expression yerine when expression kullanılacaktır

```kotlin
package org.csystem.app

fun main() = runApp()

fun runApp()
{
    while (true) {
        print("a?")
        val a = readln().toDouble()

        print("b?")
        val b = readln().toDouble()

        print("c?")
        val c = readln().toDouble()

        if (a == 0.0 && b == 0.0 && c == 0.0)
            break

        println(findRoots(a, b, c))
    }

    println("Tekrar yapıyor musunuz?")
}

fun calculateDelta(a: Double, b: Double, c: Double) = b * b - 4 * a * c

fun findRoots(a: Double, b: Double, c: Double) : String
{
    val delta = calculateDelta(a, b, c)

    fun calculateRoots() : String
    {
        val sqrtDelta = kotlin.math.sqrt(delta)
        return "x1 = ${(-b + sqrtDelta) / (2 * a)}, x2 = ${(-b - sqrtDelta) / (2 * a)}"
    }

    return when {
            delta > 0 -> calculateRoots()
            delta == 0.0 -> "x1 = x2 = ${-b / (2 * a)}"
            else -> "No real root"
    }
}
```

>*when ifadesinin in ve !in (not in) operatörleri ile kullanımı. in ve !in operatörleri ileride ele alınacaktır*

```kotlin
package org.csystem.app

fun main()
{
    print("Bir sayı giriniz:")
    val value = readln().toInt()

    when (value) {
        in 10..20 -> println("10 <= value <= 20")
        !in 1..3 -> println("value < 1 || value > 3")
        else -> println("Geçersiz değer")
    }
}
```

>*Sınıf Çalışması: Parametresi ile aldığı gün, ay ve yıl bilgisine ilişkin tarihin haftanın hangi gününe geldiğini döndüren getDayOfWeek global fonksiyonunu aşağıdaki açıklamalara uygun olarak yazınız.*

Açıklama:

- Aşağıdaki test kodu ile fonksiyonlarınız için genel bir test yapınız
- Programda tarih zamana ilişkin sınıflar kullanılmayacaktır.
- getDayOfWeek fonksiyonu 1.1.1900 tarihinden sonraki tarihler için çalışacaktır.
- Fonksiyonlar geçersiz bir tarih için -1 değerini döndürecektir
- Haftanın günü bilgisi, 1.1.1900 ile verilen tarih arasındaki toplam gün sayısı hesaplanıp 7 değerine
göre modu alınarak bulunabilir. Bu değere göre sıfır pazar, 1 pazartesi, ..., 6 değeri de Cumartesi
gününe karşılık gelir
- Programda dizi kullanılmayacaktır
- Aşağıdaki fonksiyonların kesinlikle yazılması koşuluyla istediğiniz fonksiyonu ekleyebilirsiniz.
- Yazılmış fonksiyonlar içerisinde değişiklik yapabilirsiniz. Ancak test etmeniz gerektiğini unutmayınız
- Çözüm şu ana kadar gördüğümüz konular kullanılarak yapılacaktır
- String referansına geri dönen fonksiyonlarda String sınıfını kullanmanız gerekmez. String literal oluştururak
yapınız

(İleride daha iyisi yazılacaktır)

```kotlin
package org.csystem.app

fun main() = runtTest();

fun runtTest()
{
    while (true) {
        print("Gün?")
        val day = readInt()

        if (day <= 0)
            break

        print("Ay?")
        val month = readInt()

        print("Yıl?")
        val year = readInt()

        displayDateTR(day, month, year)
        displayDateEN(day, month, year)
    }

    println("Tekrar yapıyor musunuz?")
}

fun readInt() = readln().toInt()

fun displayDateTR(day: Int, month: Int, year: Int)
{
    val dayOfWeek = getDayOfWeek(day, month, year)

    println(when {
        dayOfWeek >= 0 -> "$day ${getMonthNameTR(month)} $year ${getDayOfWeekTR(dayOfWeek)}"
        else -> "Geçersiz Tarih"
    })
}

fun displayDateEN(day: Int, month: Int, year: Int)
{
    val dayOfWeek = getDayOfWeek(day, month, year)

    println(when {
        dayOfWeek >= 0 -> "$day${getDaySuffix(day)} ${getMonthNameEN(month)} $year ${getDayOfWeekEN(dayOfWeek)}"
        else -> "Invalid Date"
    })
}

fun getDaySuffix(day: Int) :String
{
    return when (day) {
        1, 21, 31 -> "st"
        2, 22 -> "nd"
        3, 23 -> "rd"
        else -> "th"
    }
}

fun getDayOfWeekTR(dayOfWeek: Int) : String
{
    return when (dayOfWeek) {
        0 -> "Pazar"
        1 -> "Pazatesi"
        2 -> "Salı"
        3 -> "Çarşamba"
        4 -> "Perşembe"
        5 -> "Cuma"
        else -> "Cumartesi"
    }
}

fun getMonthNameTR(month: Int) : String
{
    return when (month) {
        1 -> "Ocak"
        2 -> "Şubat"
        3 -> "Mart"
        4 -> "Nisan"
        5 -> "Mayıs"
        6 -> "Haziran"
        7 -> "Temmuz"
        8 -> "Ağustos"
        9 -> "Eylül"
        10 -> "Ekim"
        11 -> "Kasım"
        else -> "Aralık"
    }
}

fun getDayOfWeekEN(dayOfWeek: Int) : String
{
    return when (dayOfWeek) {
        0 -> "Sun"
        1 -> "Mon"
        2 -> "Tue"
        3 -> "Wed"
        4 -> "Thu"
        5 -> "Fri"
        else -> "Sat"
    }
}

fun getMonthNameEN(month: Int) : String
{
    return when (month) {
        1 -> "Jan"
        2 -> "Feb"
        3 -> "Mar"
        4 -> "Apr"
        5 -> "May"
        6 -> "Jun"
        7 -> "Jul"
        8 -> "Aug"
        9 -> "Sep"
        10 -> "Oct"
        111 -> "Nov"
        else -> "Dec"
    }
}

fun getDayOfWeek(day: Int, month: Int, year: Int) : Int
{
    if (year < 1900)
        return -1

    val totalDays = getDayOfYear(day, month, year)

    if (totalDays == -1)
        return -1

    return (totalDays + getTotalDays(year)) % 7
}

fun getTotalDays(year: Int) : Int
{
    var totalDays = 0

    for (y in 1900 until year)
        totalDays += if (isLeapYear(y)) 366 else 365

    return totalDays
}

fun getDayOfYear(day: Int, month: Int, year: Int) : Int
{
    if (!isValidDate(day, month, year))
        return -1

    var dayOfYear = day

    for (m in (month - 1) downTo 1)
        dayOfYear += getDaysOfMonth(m, year)

    return dayOfYear
}

fun getDaysOfMonth(month: Int, year: Int) : Int
{
    return when (month) {
        4, 6, 9, 11 -> 30
        2 -> if (isLeapYear(year)) 29 else 28
        else -> 31
    }
}

fun isValidDate(day: Int, month: Int, year: Int) = day in 1..31 && month in 1..12 && day <= getDaysOfMonth(month, year)

fun isLeapYear(year: Int) = year % 4 == 0 && year % 100 != 0 || year % 400 == 0
```

>*T1 ve T2 türleri için*

    var a: T1
    var b: T2
    değişkenleri için
    a = b

    işleminde b'nin türüne kaynak tür (source type), a'nın türüne hedef tür (target type) denir. Bu ifadede bir doğrundan atama (dönüşüm) (implicit conversion) vardır. Kotlin'de de "implicit ve explicit conversion"
    kuralları belirlidir. Ancak Kotlin'de genel olarak farklı türler birbirine doğrudan atanamaz

>*Kotlin'de genel olarak farklı türler birbirine doğrudan (implicit) atanamaz*

```kotlin
package org.csystem.app

fun main()
{
    val a = 10
    var b : Long = a //error

    //...
}
```

>*Aşağıdaki örnekte istisna bir durum vardır. Int türden  bir sabit Long türüne doğrudan atanabilir veya ilkdeğer olarak verilebilir*

```kotlin
package org.csystem.app

fun main()
{
    var a : Long = 10

    //...
}
```

>*Kotlin'de Java' da olduğu gibi Byte ve Short türden sabit yoktur ancak sınırlar içerisinde kalması koşuluyla Int türden bir sabit Byte veya Short türüne doğrudan atanabilir*

```kotlin
package org.csystem.app

fun main()
{
    var a : Short = 10

    a = 10L //error: Sabit long türden
    a = 40000 //error: Short türünün sınırları dışında

    //...
}
```

>*Yukarıdaki istisna durum Java'da char türü için de vardır. Ancak Kotlin'de Char türü için bu durum geçersizdir*

```kotlin
package org.csystem.app

fun main()
{
    var a : Char = 67

    //...
}
```

>*Temel türlere ilişkin sınıfların toXXX metotları ile birbirlerine dönüşümleri sağlanabilir. Ancak Kotlin 1.4 ile bazı metotlarda değişiklikler olmuştur. Bu durum ileride ele alınacaktır*

```kotlin
package org.csystem.app

fun main()
{
    val a = 10
    val b : Long = a.toLong()

    println("b = $b")
}
```

>*Anımsanacağı gibi küçük tamsayı türünden büyük tamsayı türüne yapılan dönüşümde kaynak türe ilişkin değer pozitif ise sayının eklenen yüksek anlamlı byte değerlerine ilişkin bitler sıfır ile beslenir. Sayının işareti negatif ise işaretin kaybolmaması için sayının yüksek anlamlı byte değerlerine ilişkin bitler 1(bir) ile beslenir*

```kotlin
package org.csystem.app

fun main()
{
    print("Bir sayı giriniz:")
    val a = readln().toShort()
    val b = a.toInt()

    println("a = %d".format(a))
    println("a = 0x%04X".format(a))
    println("----------------------------------------------")
    println("b = %d".format(b))
    println("b = 0x%08X".format(b))
}
```

>*Anımsanacağı gibi küçük tamsayı türünden büyük tamsayı türüne yapılan dönüşümde kaynak türe ilişkin değer pozitif ise sayının eklenen yüksek anlamlı byte değerlerine ilişkin bitler sıfır ile beslenir. Sayının işareti negatif ise işaretin kaybolmaması için sayının yüksek anlamlı byte değerlerine ilişkin bitler 1(bir) ile beslenir*

```kotlin
package org.csystem.app

fun main()
{
    print("Bir sayı giriniz:")
    val a = readln().toByte()
    val b = a.toInt()

    println("a = %d".format(a))
    println("a = 0x%02X".format(a))
    println("------------------------------------------")
    println("b = %d".format(b))
    println("b = 0x%08X".format(b))
}
```

>*Temel türlere ilişkin toChar metotları*

```kotlin
package org.csystem.app

fun main()
{
    val ch: Char = 67.toChar()

    println("ch = ${ch}")
}
```

>**_Anahtar Notlar:_** Double ve Float sınıflarının toChar metotları Kotlin 1.5 ile birlikte deprecated olmuşlardır. Bu dönüşümün yapılması istendiğinde önce toInt metodu çağrılıp, sonra toChar metodu çağrılabilir

>*İki Char toplama işlemine sokulamaz. Yani Char türünün Char türden parametreli toplama (plus) operatör fonksiyonu yoktur*

```kotlin
package org.csystem.app

fun main(args:Array<String>)
{
    val ch1 = 'a'
    val ch2 = 'b'

    var ch = ch1 + ch2 //error

    //...
}
```

>*İki Char birbirinden çıkartılabilir. Bu durumda sonuç Int türden elde edilir. Bu bize iki karakterin karakter tablosundaki sıra numarası farkını verir. Şüphesiz yine bu işlem Char sınıfının Char parametreli minus operatör fonksiyonu ile yapılmaktadır*

```kotlin
package org.csystem.app

fun main()
{
    val ch1 = 'A'
    val ch2 = 'a'
    val result = ch1 - ch2

    println(result.javaClass.name) //Int
    println(result)
}
```

>*Char türü ile Int türünün toplanması durumunda sonuç Char türden çıkar. Şüphesiz bu işlem Char sınıfının Int parametreli plus fonksiyonu ile yapılmaktadır*

```kotlin
package org.csystem.app

fun main()
{
    val ch1 = 'D'
    val ch = ch1 + 32

    println(ch.javaClass.name) //Char
    println(ch)
}
```

>*Aşağıdaki örnekte Int sınıfının Char parametreli plus fonksiyonu olmadığından error oluşur*

```kotlin
package org.csystem.app

fun main()
{
    val ch1 = 'D'
    val ch = 32 + ch1 //error


}
```

>*Long türünden Int türüne dönüşüm. Aşağıdaki örnekte çeşitli değerler girip sonucu gözlemleyiniz*

```kotlin
package org.csystem.app

fun main()
{
    while (true) {
        print("Bir sayı giriniz:")
        val a = readln().toLong()
        val b: Int = a.toInt()

        println("a = %d".format(a))
        println("a = %016X".format(a))
        println("b = %d".format(b))
        println("b = %08X".format(b))

        if (a == 0L)
            break
    }
}
```

>*Byte sınıfının toChar metodu Kotlin 1.5 ile birlikte deprecated durumdadır. Bu metot yerine Int türüne dönüştürülüp toChar metodu çağrılmalıdır:<br>`a.toInt().toChar()`<br>Char sınıfının toInt metodu da deprecated olmuştur. Bunun Char türünün code property elemanı ile karaktere karşılık gelen sıra numarası Int türden elde edilebilir<br>Aşağıdaki örnekte çeşitli değerler girerek sonuçları gözlemleyiniz*

```kotlin
package org.csystem.app

fun main()
{
    while (true) {
        print("Bir sayı giriniz:")
        val a = readln().toByte()
        val b: Char = a.toInt().toChar() //a.toChar() deprecated

        println("a = %d".format(a))
        println("a = %02X".format(a))
        println("b = %c".format(b))
        println("b = %02X".format(b.code.toShort()))

        if (a.toInt() == 0)
            break
    }
}
```

**_Anahtar Notlar:_** Gerçek sayı türlerinden Short ve Byte türlerine dönüştüren toShort ve toByte fonksiyonları kaldırılmıştır. Ayrıca gerçek sayı türlerinin toChar fonksiyonları da deprecated olmuştur. Bu anlamda bu 3 tür için toInt fonksiyonu çağrılarak Int türüne dönüştürme yapıp sonradan ilgili türlere dönüştürecek fonksiyonların çağrılması gerekir. Bu anlamda gerçek sayı türlerinden tamsayı türlerine dönüşüm ya Int türüne ya da Long türüne yapılabilmektedir

>*Gerçek sayı türünden tamsayı türüne dönüşüm aşağıdaki gibi gerçekleşir:*

1. Sayının noktadan sonraki kısmı atılır.
2. Elde edilen sayı hedef türün sınırları içerisindeyse doğrudan atanır. Elde edilen sayı hedef türün sınırları içerisinde değilse

- Hedef tür Int türü ise sayının pozitif ve negatif olması durumuna göre Int türünün en büyük veya en küçük değeri
    alınır ve atanır
- Hedef tür Long türü ise sayının pozitif veya negatif olma durumuna göre Long türünün en büyük ya da en küçük değer alınır.
ve atanır

```kotlin
package org.csystem.app

fun main()
{
    print("Bir sayı giriniz:")
    val a = readln().toDouble()
    val b: Byte = a.toByte() //error
    var c: Short = a.toShort() //error
}
```

```kotlin
package org.csystem.app

fun main()
{
    print("Bir sayı giriniz:")
    val a = readln().toDouble()
    val b: Char = a.toChar() //deprecated since Kotlin 1.5

}
```

```kotlin
package org.csystem.app

fun main()
{
    val a = 300.4
    val b = a.toInt()

    println(b)
}
```

```kotlin
package org.csystem.app

fun main()
{
    val a: Double = 4_000_000_000.456
    val b : Int = a.toInt()
    val c : Int = 4_000_000_000.toInt()
    val d: Int = Int.MAX_VALUE

    println("a = $a")
    println("b = $b")
    println("c = $c")
    println("d = $d")
}
```

```kotlin
package org.csystem.app

fun main()
{
    val a: Double = -4_000_000_000.456
    val b : Int = a.toInt()
    val c : Int = (-4_000_000_000).toInt()
    val d: Int = Int.MIN_VALUE

    println("a = $a")
    println("b = $b")
    println("c = $c")
    println("d = $d")
}
```

**_Anahtar Notlar:_** Kotlin'de temel türlere ilişkin dönüşümler (explicit veya implicit) aslında operatör fonksiyonları ile yapılır. Burada basitleştirmek için işlem öncesi otomatik tür denüşümü diyeceğiz.

>*İşlem öncesi otomatik tür dönüşümlerine ilişkin operatör fonksiyonları*

```kotlin
package org.csystem.app

fun main()
{
    val a: Int = 10
    val b: Long = 456
    val c = a + b // a.plus(b)
    val d = b + a //b.plus(a)

    println(c.javaClass.name)

    println("c = $c")
    println("d = $d")
}
```

>*Bazen tür dönüştürme işlemi yapılmazsa bilgi kaybı oluşabilir. Aşağıdaki örnekte a için toLong çağrılmasaydı bilgi kaybı oluşabilirdi*

```kotlin
package org.csystem.app

fun main()
{
    print("Birinci sayıyı giriniz:")
    val a = readln().toInt()

    print("İkinci sayıyı giriniz:")
    val b = readln().toInt()
    val c = a.toLong() + b

    println("c = $c")
}
```

>*Sınıf Çalışması: Klavyeden sıfır girilene kadar alınan Int türden sayıların ortalamasını bulup ekrana yazdıran programı yazınız*

```kotlin
package org.csystem.app

fun main() = runAverageApplication()

fun runAverageApplication()
{
    var sum = 0
    var count = 0
    println("Sayıları girmeye başlayınız:")

    while (true) {
        val a = readln().toInt()

        if (a == 0)
            break

        sum += a; ++count
    }

    println("Ortalama:${sum.toDouble() / count}")
    println("Tekrar yapıyor musunuz?")
}
```

### Sınıf bildirimi ###

```kotlin
class SerialPort

class DeviceInfo

class Person {
    //...
}
```

>*Kotlin'de new operatörü yoktur. Nesne yaratılması aşağıdaki gibi yapılabilir*

```kotlin
package org.csystem.app

fun main()
{
    var d = DeviceInfo()

    //...
}

class DeviceInfo
```

**_Anahtar Notlar:_** Kotlin'de sınıfın bir elemanına (member) herhangi bir erişim belirleyici anahtar sözcük yazmamak (no-modifier) "public" anlamına gelir. Biz yazmamayı tercih edeceğiz

**_Anahtar Notlar:_** Anımsanacağı gibi Java'da no-modifier bir eleman aynı paketteki diğer sınıflar için "public", farklı paketteki sınıflar için "private" anlamındadır*

>*Kotlin'de new operatörü yoktur. Nesne yaratılması aşağıdaki gibi yapılabilir*

```kotlin
package org.csystem.app

fun main()
{
    val d = DeviceInfo()

    d.connect("192.167.56.23", 33000)
}

class DeviceInfo {
    fun connect(host: String, port: Int)
    {
        println("Connect to device on $host:$port")
    }
}
```

>*Sınıfın public primary ctor'u*

```kotlin
package org.csystem.app

fun main()
{
    var s = Sample(10, 3.4)

    //...
}

class Sample(a: Int, b: Double) {
    //...
}
```

>*Sınıfın primary ctor'unun bildiriminde constructor anahtar sözcüğü kullanılabilir*

```kotlin
package org.csystem.app

fun main()
{
    var s = Sample(10, 3.4)

    //...
}

class Sample constructor (a:Int, b:Double)
```

>*Sınıfın primary constructor bildiriminde sınıf isminden sonra,  ctor bildiriminden önce bildirime ilişkin bazı özellikler yazılacak constructor anahtar sözcüğü zorunludur. Örneğin erişim belilrleyicisi yazılacaksa constructor anahtar sözcüğü zorunludur*

```kotlin
package org.csystem.app

class Sample private constructor(a:Int, b:Double)
```

>*primary constructor'ın parametreleri var veya val olarak bildirildiklerinde sınıfın property elemanları bildirilmiş olur. property elemanı doğrudan veri elemanı değildir. Aşağıdaki kullanımı veri elemanı biçiminde düşünülebilir*

```kotlin
package org.csystem.app

fun main()
{
    val s = Sample(10, 3.4)

    println("s.a = ${s.a}, s.b = ${s.b}")

    ++s.a

    println("s.a = ${s.a}, s.b = ${s.b}")
}

class Sample(var a: Int, val b: Double)
```

>*primary constructor'ın parametreleri var veya val olarak bildirildiklerinde sınıfın property elemanları bildirilmiş olur. property elemanı doğrudan veri elemanı değildir. Aşağıdaki kullanımı veri elemanı biçiminde düşünülebilir*

```kotlin
package org.csystem.app

fun main()
{
    val s = Sample(10, 3.4)

    println("s.a = ${s.a}")

    ++s.a

    println("s.a = ${s.a}")
}

class Sample(var a: Int, b: Double)
```

>*Sınıfın property elemanları primary ctor içerisinde bildirilmişse erişim belirleyicisi de verilebilir. Şüphesiz default erişim belirleyici public'tir*

```kotlin
package org.csystem.app

fun main()
{
    val s = Sample(10, 3.4)

    println("s.a = ${s.a}")
    println("s.b = ${s.b}") //error
}

class Sample(var a: Int, private var b: Double) {
    //...
}
```

>*Sınıfın primary constructor'ının kodları init isimli bir blok içerisinde yazılabilir. init bloğu içerisinde primary constructor'ın parametrelerine veya primary constructor içerisinde bildirilmiş property elemanlarına erişilebilir*

```kotlin
package org.csystem.app

fun main()
{
    val s = Sample(10, 3.4)

    //...
}

class Sample(var a: Int, private var b: Double) {
    init {
        println("primary ctor: a = $a, b = $b")
        //...
    }
    //...
}
```

>*Sınıfın primary ctor'u. Aşağıdaki örnek tamamen konuyu anlatmak için yazılmıştır. Sınıf ileride daha profesyonel olarak yazılacaktır*

```kotlin
package org.csystem.app

fun main()
{
    val c1 = Circle(10.3)
    val c2 = Circle()

    //...
}

class Circle(private var radius: Double = 0.0) {
    init {
        radius = kotlin.math.abs(radius)
    }
    //...
}
```

>*Bir sınıfın  primary ctor'u olmak zorunda değildir. Bu kural bazı sınıflar için söz konusu değildir. Örneğin "data sınıfları (data class)" için primary ctor yazmak zorunludur. "data sınıfları" ileride ele alınacaktır*

```kotlin
package org.csystem.app

fun main()
{
    var s = Sample()

    //...
}

class Sample {
    //...
}
```

>*Sınıfın secondary ctor elemanı. Bir secondary ctor constructor abahtar sözcüğü ile bildirilir. Sınıfın secondary ctor'u overload edilebilir. primary ctor bir tanedir*

```kotlin
package org.csystem.app

fun main()
{
    var s = Sample(3)
    var k = Sample(2.3)

    //...
}

class Sample {
    constructor(a: Int)
    {
        println("I am a secondary ctor with parameter type: Int")
        println("a = $a")
        //...
    }

    constructor(a: Double)
    {
        println("I am a secondary ctor with parameter type: Double")
        println("a = $a")
        //...
    }

    //...
}
```

>*Sınıfın primary ctor'u varsa tüm secondary ctor'ların doğrudan ya da dolaylı olarak bu ctor'u çağırıyor olmaları gerekir. Bu işlem :this ctor sentaksı ile yapılır*

```kotlin
package org.csystem.app

fun main()
{
    var s1 = Sample(3)
    println("-----------------------------------------------")
    var s2 = Sample(2.3)
    println("-----------------------------------------------")
    var s3 = Sample(2F)
    println("-----------------------------------------------")

    //...
}

class Sample(var x: Double) {
    init {
        println("I am a primary ctor")
        println("x = $x")
    }

    constructor(a: Int) : this(a.toFloat())
    {
        println("I am a secondary ctor with parameter type: Int")
        println("a = $a")
        //...
    }

    constructor(a: Float) : this(a.toDouble())
    {
        println("I am a secondary ctor with parameter type: Float")
        println("a = $a")
        //...
    }

    //...
}
```

>*Sınıfın secondary constructor'larının parametreleri property elemanı olamaz. Yani var veya val anahtar sözcükleri bu değişkenler için kullanılamaz*

```kotlin
package org.csystem.app


class Sample(var a: Int) {
    constructor(var b: Double) : this(12) //error
    {

    }
}
```

>*Secondary ctor'lar gövdesiz olabilir*

```kotlin
package org.csystem.app

fun main()
{
    var s = Sample(34)
    var k = Sample(3.5)
    //...

}

class Sample(var a : Double) {
    init {
        println("primary ctor")
        println("a = $a")
    }
    constructor(b: Int) : this(b.toDouble())
}
```

>*Aşağıdaki örnekte default ctor primary ctor yapılmıştır*

```kotlin
package org.csystem.app

fun main()
{
    var s1 = Sample(10)
    println("---------------------------")
    var s2 = Sample(4.5)
    println("---------------------------")
    var s3 = Sample()
    println("---------------------------")

    //...
}

class Sample() {
    init {
        println("primary constructor")
    }

    constructor(b: Double) : this()
    {
        println("constructor(Double)")
    }

    constructor(b: Int) : this(b.toDouble())
    {
        println("constructor(Int)")
    }
}
```

>*Aşağıdaki örnekte hem default hem de int/Int parametreli ctor Java/Kotlin arakodunda bulunacaktır. Kotlin ile yazılan kodların Java'dan kullanımı ve Java'da yazılan kodların Kotlin'den kullanımı detayları ileride ele alınacaktır*

```kotlin
package org.csystem.app

fun main()
{
    val s = Sample()
    val k = Sample(20)

    //...
}

class Sample(a: Int = 10) {
    init {
        println("primary constructor: a = $a")
    }
}
```

>*Aşağıdaki örneği inceleyiniz. Örnekte default constructor yazılmıştır. Dolayıysıyla secondary default ctor çağrılır (best match). Örnek için primary ctor'un parametresi için verilen default argüman artık kullanışsızdır*

```kotlin
package org.csystem.app

fun main()
{
    val s = Sample()
    println("--------------------------------")
    val k = Sample(30)

    println("s.a = ${s.a}")
    println("k.a = ${k.a}")

}

class Sample(val a: Int = 10) {
    init {
        println("primary constructor: $a")
    }

    constructor() : this(20)
    {
        println("default constructor")
    }
}
```

>*Aşağıdaki iskelet kodu inceleyiniz:<br>Aşağıdaki Time sınıfının yaklaşık Java karşılığı:*

```java
    package org.csystem.app;

    class Time {
        private int m_hour, m_minute, m_second, m_millisecond;

        //...

        public Time(int h, int m)
        {
            this(h, m, 0);
        }

        public Time(int h, int m, int s)
        {
            this(h, m, s, 0);
        }

        public Time(int h, int m, int s, int ms)
        {
            //...
        }
    }*
```

```kotlin
package org.csystem.app

class Time(var hour: Int, var min: Int, var sec: Int, var msec: Int) {
    init {
        //...
    }
    constructor(h: Int, m: Int, s: Int) : this(h, m, s, 0)
    constructor(h: Int, m: Int) : this(h, m, 0)

    //...
}
```

>*Örnek, default argüman kullanılarak daha Kotlin mantığıyla yazılabilir*

>*Aşağıdaki Time sınıfının yaklaşık Java karşılığı:*

```java
    package org.csystem.app;

    class Time {
        private int m_hour, m_minute, m_second, m_millisecond;

        //...

        public Time(int h, int m)
        {
            this(h, m, 0);
        }

        public Time(int h, int m, int s)
        {
            this(h, m, s, 0);
        }

        public Time(int h, int m, int s, int ms)
        {
            //...
        }
    }
```

```kotlin
package org.csystem.app

class Time(var hour: Int, var min: Int, var sec: Int = 0, var msec: Int = 0) {
    init {
        //...
    }
    //...
}
```

>*primary ctor olmasa bile init bloğu yazılabilir. Bu durumda tüm ctor'ların başında bu blok çalıştırılır. Java'daki non-static initializer ile neredeyse aynı anlamdadır*

```kotlin
package org.csystem.app

fun main()
{
    var s = Sample()
    println("--------------------------")
    var k = Sample(10)
    //...
}

class Sample {
    init {
        println("init")
    }

    constructor()
    {
        println("default constructor")
    }

    constructor(a: Int)
    {
        println("Int constructor")
    }
}
```

>*Aşağıdaki örnekte sırasıyla primary constructor, Int Double parametreli secondary constructor ve Double parametreli secondary constructor çağrılır*

```kotlin
package org.csystem.app

fun main()
{
    var s = Sample(3.4)

    //...
}

class Sample(var a: Int = 0) {
    init {
        println("primary constructor")
    }

    constructor(a: Int, b: Double) : this(a)
    {
        println("constructor(Int, Double)")
    }

    constructor(b:Double) : this(0, b)
    {
        println("constructor(Double)")
    }
}
```

#### Point sınıfı ####

```kotlin
package org.csystem.app

fun main()
{
    val p1 = Point(100.0, 100.0)
    val p2 = Point(100, 100)
    val p3 = Point()

    println("(${p1.x}, ${p1.y})")
    println("(${p2.x}, ${p2.y})")
    println("(${p3.x}, ${p3.y})")
}
```

```kotlin
package org.csystem.app

fun main()
{
    val p1 = Point(234.0, -456.7)
    val p2 = Point(230.0, -453.7)

    println("Distance: ${p1.distance(p2)}");
    println("Distance: ${p1.distance()}");

}
```

```kotlin
package org.csystem.app

fun main()
{
    val p = Point(100.0, 100.0)

    println("(${p.x}, ${p.y})")

    p.offset(20.0, -20.0)

    println("(${p.x}, ${p.y})")

    p.offset(30.0)

    println("(${p.x}, ${p.y})")

}

class Point(var x: Double = 0.0, var y: Double = 0.0) {
    constructor(x: Int, y: Int) : this(x.toDouble(), y.toDouble()) //optional
    fun distance(a: Double = 0.0, b: Double = 0.0) = kotlin.math.sqrt((x - a) * (x - a) + (y - b) * (y - b))
    fun distance(other: Point) = distance(other.x, other.y)

    fun offset(dx: Double, dy: Double = dx)
    {
        x += dx
        y += dy
    }
}
```

>*Aşağıdaki kodda iki anlamlılıktan (ambiguity) dolayı error oluşur*

```kotlin
package org.csystem.app

fun main()
{
    val s = Sample();

    //...
    s.foo() //error: ambiguity
}


class Sample {
    fun foo(a: Int = 0, b: Double = 4.5) = println("foo(Int, Double)")
    fun foo(s: Sample = Sample(), b: Int = 34) = println("foo(Sample, Int)")
}
```

>*Aşağıdaki kodda tam uyumdan (best match) dolayı error oluşmaz*

```kotlin
package org.csystem.app

fun main()
{
    val s = Sample();

    //...
    s.foo()
}

class Sample {
    fun foo(a:Int = 0, b: Double = 4.5) = println("foo(Int, Double)")
    fun foo(s: Sample = Sample(), b: Int = 34) = println("foo(Sample, Int)")
    fun foo() = println("foo()")
}
```

>*Aşağıdaki kodda iki anlamlılıktan dolayı ambiguity oluşur*

```kotlin
package org.csystem.app

fun main()
{
    val s = Sample();

    //...
    s.foo(10) //error
}

class Sample {
    fun foo(a: Int = 34, b: Float = 4.5F) = println("foo(Int, Float)")
    fun foo(a:Int = 0, b: Double = 4.5) = println("foo(Int, Double)")
    fun foo() = println("foo()")
}
```

>*Aşağıdaki örnekte Int, Float ve Long, Double parametreli foo metotları "uygun (applicable)" metotlardır. Çünkü metot çağrısında argüman olarak verilen Int türden ifade bir sabit olduğundan Long türünme de doğrudan (implicit) dönüşebilir*

```kotlin
package org.csystem.app

fun main()
{
    val s = Sample();

    //...
    s.foo(10)
}

class Sample {
    fun foo(a: Int = 34, b: Float = 4.5F) = println("foo(Int, Float)")
    fun foo(a: Long = 0, b: Double = 4.5) = println("foo(Long, Double)")
    fun foo() = println("foo()")
}
```

>*Aşağıdaki örnmeği inceleyiniz*

```kotlin
package org.csystem.app

fun main()
{
    val s = Sample();
    val a = 10

    //...
    s.foo(a)
}

class Sample {
    fun foo(a: Int = 34, b: Float = 4.5F) = println("foo(Int, Float)")
    fun foo(a: Long = 0, b: Double = 4.5) = println("foo(Long, Double)")
    fun foo() = println("foo()")
}
```

>*Bir property elemanının set bölümü ona atama yapıldığında çalışır. get bölümü ise değeri kullanılmak istendiğinde çalışır. Bir property elemanı için genel olarak arka planda yaratılan bir veri elemanı (backing field) bulunmaktadır. Property elemanı içerisinde, ilişkin olduğu veri elemanına erişmek için field bağlamsal anahtar sözcüğü (contextual keyword) kullanılabilir.<br>Aşağıdaki örneğin Java karşılığı yaklaşık olarak aşağıdaki gibidir:*

```java
package org.csystem.app;

    class App {
        public static void main(String [] args)
        {
            Sample s = new Sample();

            s.setX(10);

            System.out.println(s.getX() * 2);
            System.out.println(s.getX());
        }
    }

    class Sample {
        private int field;

        public int getX()
        {
            System.out.println("getX");
            return field;
        }

        public void setX(int value)
        {
            System.out.println("set");
            field = value;
        }
    }
```

```kotlin
package org.csystem.app

fun main()
{
    val s = Sample()

    s.x = 10

    println(s.x)
    println(s.x * 2)
}

class Sample {
    var x: Int = 0
        set(value)
        {
            println("set")

            field = value
        }
        get()
        {
            println("get")
            return field
        }
}
```

>*Aşağıdaki örnekte x ve y property elemanları için get ve set bölümleri otomatik olarak yazılmıştır*

```kotlin
package org.csystem.app

fun main()
{
    val s = Sample()

    s.z = 3.4
    s.x = 34
    s.y = 345

    println("${s.x}, ${s.y}, ${s.z}")
}


class Sample {
    var x = 0
    var y: Int
    var z: Double = 0.0
        set(value)
        {
            //...
            field = value //backing field
        }

    init {
        y = 20
    }
}
```

>*Aşağıdaki örnekte x property elemanı için private set yapılarak sınıf içerisinde atama yapılabilir ancak sınıf dışında readonly duruma getirilmiş olur*

```kotlin
package org.csystem.app

fun main()
{
    val s = Sample()

    s.x = 23 //error

    println(s.x)
}

class Sample {
    var x: Int = 0
        private set

    init {
        x = 34
    }

    fun foo(a: Int)
    {
        x = a
    }
}
```

>*Aşağıdaki örnekte Sample sınıfının val olarak bildirilmiş property elemanına ilkdeğer (initialization) verilmediği için tüm ctor'larda değer verilmek zorundadır. Sample sınıfında default ctor içerisinde `:this` ctor sentaksı kullanıldığından, dolaylı olarak değer verilmiş olur. Mample sınıfında ise val olarak bildirilmiş x property elemanı için ilkdeğer verildiğinden ctor'lar içerisinde değer verilemez. Test sınıfında ise init bloğu içerisinde val olarak bildirilmiş x property elemanına değer verildiğinden artık ctor'lar içerisinde (aslında hiçbir yerde) değer verilemez*

```kotlin
package org.csystem.app

fun main()
{
    val s = Sample()

    s.x = 23 //error

    println(s.x)
}

class Test {
    val x: Int

    init {
        x = 0
    }

    constructor(a: Int)
    {
        //...
        x = a //error
    }

    constructor() : this(0)
    {
        //...
    }

    //...
}

class Mample {
    val x: Int = 10

    constructor(a: Int)
    {
        //...
        x = a //error
    }

    constructor() : this(0)
    {
        //...
    }
}

class Sample {
    val x: Int

    constructor(a: Int)
    {
        //...
        x = a;
    }

    constructor() : this(0)
    {
        //...
    }

    //...
}
```

>*Dikkat aşağıdaki örnekte r değeri yalnızca ctor içerisinde kontrol edilebilir. Atama durumunda kontrol işlemi yazılmamıştır Bu anlamda Circle sınıfı düzgün olarak tasarlanmamıştır*

```kotlin
package org.csystem.app

fun main()
{
    val c = Circle(-3.9)

    println(c.r)

    c.r = 3.4

    println(c.r)

    c.r = -4.9 // Dikkat pozitif yapılmıyor

    println(c.r)
}

class Circle(var r: Double) {
    init {
        r = kotlin.math.abs(r)
    }
}
```

>*Circle sınıfı ve test kodu<br>(Daha karmaşık ve tam olarak Kotlin mantığıyla tasarlanmamış versiyon)<br>Aşağıdaki yaklaşımda backing field yani set kısmı gereksizdir*

```kotlin
package org.csystem.app

fun main()
{
    val  c = Circle(-3.4)

    println(c.area)
    println(c.circumference)
    println("------------------------------")

    c.r = -4.4

    println(c.area)
    println(c.circumference)
    println("------------------------------")
}

class Circle(radius: Double = 0.0) {
    var r: Double = Math.abs(radius)
        set(value) {
            field = kotlin.math.abs(value)
            area = Math.PI * field * field
            circumference = 2 * Math.PI * field
        }

    var area: Double =  kotlin.math.PI * r * r
        private set
        get() = kotlin.math.PI * r * r

    var circumference : Double = 2 *  kotlin.math.PI * r
        private set
        get() = 2 *  kotlin.math.PI * r;
}
```

>*Circle sınıfı ve test kodu<br>(Daha profesyonel versiyon)<br>Aşağıdaki örnekte area ve circumference property elemanları için backing field yaratılmaz<br>Not: Circle sınıfı ileride göreceğimiz konular ile daha profesyonel yazılacaktır.<br>Aşağıdaki Circle sınıfının yaklaşık Java karşılığı şu şekildedir:*

```java
    package org.csystem.math.geometry;

    public class Circle {
        private double m_r;

        public Circle()
        {}

        public Circle(double r)
        {
            setRadius(r);
        }

        public double getRadius()
        {
            return m_r;
        }

        public void setRadius(double r)
        {
            m_r = abs(r);
        }

        public double getArea()
        {
            return PI * m_r * m_r;
        }

        public double getCircumference()
        {
            return 2 * PI * m_r;
        }
    }
```

```kotlin
package org.csystem.app

import org.csystem.math.geometry.Circle

fun main()
{
    val  c = Circle(-3.4)

    println(c.area)
    println(c.circumference)
    println("------------------------------")

    c.r = -4.4

    println(c.area)
    println(c.circumference)
    println("------------------------------")
}
```

```kotlin
/*----------------------------------------------------------------------------------------------------------------------
	FILE        : Circle.kt
	AUTHOR      : Android-Mar-2023 Group
	LAST UPDATE : 19.04.2023

	Circle class that represents the circle in geometry

	Copyleft (c) 1993 by C and System Programmers Association
	All Rights Free
----------------------------------------------------------------------------------------------------------------------*/
package org.csystem.math.geometry

class Circle(r: Double = 0.0) {
    var r: Double = kotlin.math.abs(r)
        set(value) {
            field = kotlin.math.abs(value)
        }

    val area : Double
        get() = Math.PI * r * r

    val circumference : Double
        get() = 2 * Math.PI * r
}
```

>*Sınıf Çalışması: Bir karmaşık sayıyı temsil eden Complex isimli immutable sınıfı yazınız. Sınıf karmaşık sayının `0 + 0i` sayısına uzaklığı olan Norm bilgisini de verecektir<br>|a + bi| = sqrt(a * a + b * b)*

```kotlin
package org.csystem.app

import org.csystem.math.Complex

fun main()
{
    val z = Complex(3.4, 5.6)
    val zc = z.conjugate;

    println("|${z.real} + i.${z.imag}| = ${z.norm}")
    println("|${zc.real} + i.${zc.imag}| = ${zc.norm}")
}
```

```kotlin
/*----------------------------------------------------------------------------------------------------------------------
	FILE        : Complex.kt
	AUTHOR      : Android-Mar-2023 Group
	LAST UPDATE : 19.04.2023

	Immutable Complex class that represents the complex number

	Copyleft (c) 1993 by C and System Programmers Association
	All Rights Free
----------------------------------------------------------------------------------------------------------------------*/
package org.csystem.math

class Complex(val real: Double = 0.0, val imag: Double = 0.0) {
    val norm: Double
        get() = kotlin.math.sqrt(real * real + imag * imag)

    val length: Double
        get() = norm

    val conjugate: Complex
        get() = Complex(real, -imag)
}
```

>*Kotlin JVM'de rasgele üretimi için java.util paketi içerisinde Random sınıfı kullanılabilse de, Kotlin'inin standart kütüphanesi içerisinde de random sayı üretimi yapan "object" ve fonksiyonlar vardır. Bu araçlar JavaSE'deki Random sınıfı aynı algoritmayı kullanmazlar. Programcı rasgele sayı üretiminde özel bir durum yoksa Kotlin'in rasgele sayı üretimi yapan object ve fonksiyonlarını kullanmalıdır*

>*Random "object"'inin nextInt metotları*

```kotlin
package org.csystem.app

import kotlin.random.Random

fun main()
{
    for (i in 1..10)
        print("${Random.nextInt(100)} ") //[0, 100)

    println()
}
```

>*Random "object"'inin nextInt metotları*

```kotlin
package org.csystem.app

import kotlin.random.Random

fun main()
{
    print("Birinci sayıyı giriniz:")
    val a = readln().toInt();

    print("İkinci sayıyı giriniz:")
    val b = readln().toInt();

    for (i in 1..10)
        print("${Random.nextInt(a, b)} ") //[a, b)

    println()
}
```

>*Random "object"'inin nextBoolean metodu*

```kotlin
package org.csystem.app

import kotlin.random.Random

fun main()
{
    for (i in 1..10)
        println(Random.nextBoolean())
}
```

>*Sınıf Çalışması: Bir paranın yazı gelme olasığını yaklaşık olarak hesaplayan basit bir simülasyon programını yazınız*

```kotlin
package org.csystem.app

import kotlin.random.Random

fun main() = runCoinSimulationApp()

fun runCoinSimulationApp()
{
    while (true) {
        print("Parayı kaç kez atmak istersiniz?")
        val count = readln().toInt()

        if (count <= 0)
            break

        println("p = ${coinProbability(count)}")
    }

    println("Tekrar yapıyor musunuz?")
}

fun coinProbability(count: Int) : Double
{
    var n = 0

    for (i in 0 until count)
        n += Random.nextInt(2)

    return n.toDouble() / count
}
```

>*Sınıf Çalışması: Bir paranın yazı gelme olasığını yaklaşık olarak hesaplayan basit bir simülasyon programını yazınız*

```kotlin
package org.csystem.app

import kotlin.random.Random

fun main() = runCoinSimulationApp()

fun runCoinSimulationApp()
{
    while (true) {
        print("Parayı kaç kez atmak istersiniz?")
        val count = readln().toInt()

        if (count <= 0)
            break

        println("p = ${coinProbability(count)}")
    }

    println("Tekrar yapıyor musunuz?")
}

fun coinProbability(count: Int) : Double
{
    var n = 0

    for (i in 0 until count)
        if (Random.nextBoolean())
            ++n

    return n.toDouble() / count
}
```

>*Sınıf Çalışması: İki zar atma deneyinde çift (ikisinin aynı olması) gelme olasılığını yaklaşık olarak hesaplayan basit simülasyon programını yazınız*

```kotlin
package org.csystem.app

import kotlin.random.Random

fun main() = runSameDiceProbabilitySimulation()

fun runSameDiceProbabilitySimulation()
{
    while (true) {
        print("Atış sayısını giriniz:")
        val n = readln().toInt()

        if (n <= 0)
            break

        println("Yazı Gelme Olasılığı:${findSameDiceProbability(n)}")
    }

    println("Tekrar yapıyor musunuz?")
}

fun rollDice() = Random.nextInt(1, 7)

fun areSame() = rollDice() == rollDice()

fun findSameDiceProbability(n: Int) : Double
{
    var total = 0;

    for (i in 1..n)
        if (areSame())
            ++total


    return total.toDouble() / n
}
```

>*Random "object"'inin nextDouble fonksiyonu*

```kotlin
package org.csystem.app

import kotlin.random.Random

fun main()
{
    for (i in 1..10)
        println(Random.nextDouble()) //[0, 1)
}
```

>*Random "object"'inin nextDouble fonksiyonu*

```kotlin
package org.csystem.app

import kotlin.random.Random

fun main()
{
    print("Bir sayı giriniz:")
    val bound = readln().toDouble()

    for (i in 1..10)
        println(Random.nextDouble(bound)) //[0, bound)
}
```

>*Random "object"'inin nextDouble fonksiyonu*

```kotlin
package org.csystem.app

import kotlin.random.Random

fun main()
{
    print("Birinci sayıyı giriniz:")
    val a = readln().toDouble()

    print("İkinci sayıyı giriniz:")
    val b = readln().toDouble()

    for (i in 1..10)
        println(Random.nextDouble(a, b)) //[min, max)
}
```

>*Tohum değeri Random fonksiyonu ile verilerek istenilen tohum değerinden başlayan bir Random referansı elde edilebilir*

```kotlin
package org.csystem.app

import kotlin.random.Random

fun main()
{
    while (true) {
        print("Tohum değerini giriniz:")
        val seed = readln().toLong()

        val r = Random(seed)

        for (i in 1..10)
            print("${r.nextInt(0, 100)} ")

        println()

        if (seed == 0L)
            break
    }
}
```

**_Anahtar Notlar:_** java.util paketindeki Random sınıfıyla kotlin.random paketindeki Random object'inin içsel algoritmaları aynı olmak zorunda değildir

```kotlin
package org.csystem.app

import kotlin.random.Random

fun main()
{
    print("Tohum değerini giriniz:")
    val seed = readln().toLong()
    val r = Random(seed)
    val rj = java.util.Random(seed)

    for (i in 1..10)
        print("${r.nextInt(99) + 1} ")

    println()

    for (i in 1..10)
        print("${rj.nextInt(99) + 1} ")

    println()
}
```

>*Kotlin'de `==` ve `!=` operatörleri ile karşılaştırma sırasında ileride daha detaylı olarak ele alacağımız equals metodu override edilmişse bu metot çağrılır ve geri dönüş değerine göre işlem yapar. Yani bu durumda referans karşılaştırması yapmaz. equals metodu override edilmemişse referans karşılaştırması yapar.<br>data sınıflarının equals metodu içerisinde  property elemanlarını `==` işlemine sokacak şekilde otomatik olarak override edildiğinden örnekte `==` operatörü true değer üretir. Yani artık referans karşılaştırması yapmaz.data sınıfları ileride detaylı  olarak ele alınacaktır<br>Aşağıdaki örneği sınıf bildiriminde data anahtar sözcüğünü kaldırarak test ediniz ve sonucu gözlemleyiniz*

```kotlin
package org.csystem.app;

fun main()
{
    val s = Sample(10)
    val k = Sample(10)

    println(if (s == k) "Aynı nesne" else "Farklı nesneler")
}

data class Sample(var value: Int)
```

>*Kotlin'de referans karşılaştırmasının kesin olarak yapılabilmesi için iki tane ek eşitlik karşılaştırma operatörleri bulunmaktadır:*
    
`===`, `!==`<br>Bu durumda programcı kesinlikle referans karşılaştırması yapmak istiyorsa
bu operatörleri tercih etmelidir

```kotlin
package org.csystem.app;

fun main()
{
    val s = Sample(10)
    val k = Sample(10)

    println(s === k)
    println(s !== k)
}

data class Sample(var value: Int)
```

>*Her ne kadar temel türler (yani temel türlere ilişkin sınıflar) için de `===`, `!==` operatörleri kullanılabilse de anlamsız bir durum oluştuğundan bu operatörlerin temel türler için kullanımı deprecated olmuştur*

```kotlin
package org.csystem.app;

fun main()
{
    print("Birinci sayıyı giriniz:")
    val a = readln().toInt()

    print("İkinci sayıyı giriniz:")
    val b = readln().toInt()

    println(a === b) //Deprecated
    println(a == b)
}
```

>*Kotlin'in kendi standart kütüphanesi içerisinde String sınıfı bulunmaktadır. Bu sınıf genel olarak Java'nın String sınıfına benzemekle birlikte ek bir takım özelliklere de sahiptir. Özellikle String sınıfına Java 11 ile birlikte önemli eklentiler yapılmıştır. Bu anlamda Kotlin'in String sınıfı bu eklentilere ve daha fazlasına zaten sahiptir*

>*İki tırnak içerisindeki ifadeler String türündendir*

```kotlin
package org.csystem.app;

fun main()
{
    val s: String = "ankara"

    println(s)
}
```

>*Klavyeden String okunması*

```kotlin
package org.csystem.app;

fun main()
{
    print("Bir yazı giriniz:")
    val s: String = readln()

    println(s)
}
```

>*Klavyeden String okunması durumunda elde edilen nesneler farklıdır*

```kotlin
package org.csystem.app;

fun main()
{
    print("Birinci yazıyı giriniz:")
    val s = readln()

    print("İkinci yazıyı giriniz:")
    val k = readln()

    println(s === k)
}
```

>*Özdeş String atomları için aynı adres kullanılır*

```kotlin
package org.csystem.app

fun main()
{
    val s1 = "ankara"
    val s2 = "ankara"

    println(s1 == s2)
    println(s1 != s2)
    println(s1 === s2)
    println(s1 !== s2)
}
```

>*String sınıfının compareTo metodu*

```kotlin
package org.csystem.app

fun main()
{
    print("Birinci yazıyı giriniz:")
    val s = readln()

    print("İkinci yazıyı giriniz:")
    val k = readln()

    println(s.compareTo(k))
}
```

>*String sınıfının compareTo metodunun ignoreCase parametresi ile büyük küçük harf duyarsız (case insensitive) karşılaştırma yapılabilir*

```kotlin
package org.csystem.app

fun main()
{
    print("Birinci yazıyı giriniz:")
    val s = readln()

    print("İkinci yazıyı giriniz:")
    val k = readln()

    println(s.compareTo(k, true))
}
```

>*İki yazının aynı olup olmadığı `==` veya `!=` operatörleri ile test edilebilir*

```kotlin
package org.csystem.app

fun main()
{
    print("Birinci yazıyı giriniz:")
    val s = readln()

    print("İkinci yazıyı giriniz:")
    val k = readln()

    println(if (s == k) "Aynı yazı" else "Farklı yazılar")
}
```

>*İki yazının eşitliğinin case insensitive olarak karşılaştırılması*

```kotlin
package org.csystem.app

fun main()
{
    print("Birinci yazıyı giriniz:")
    val s = readln()

    print("İkinci yazıyı giriniz:")
    val k = readln()

    println(if (s.equals(k, true)) "Aynı yazı" else "Farklı yazılar")
}
```

>*String sınıfının toUpperCase metodu Kotlin 1.5 ile birlikte deprecated olmuştur. Bu fonksiyon yerine upperCase fonksiyonu kullanılmalıdır*

```kotlin
package org.csystem.app;

fun main()
{
    print("Bir yazı giriniz:")
    val s1 = readln()
    val s2 = s1.uppercase()

    println(s1)
    println(s2)
}
```

>*String sınıfının toLowerCase metodu Kotlin 1.5 ile birlikte deprecated olmuştur. Bu fonksiyon yerine lowerCase fonksiyonu kullanılmalıdır*

```kotlin
package org.csystem.app;

fun main()
{
    print("Bir yazı giriniz:")
    val s1 = readln()
    val s2 = s1.uppercase()

    println(s1)
    println(s2)
}
```

>*String sınıfının indexOf metotları*

```kotlin
package org.csystem.app

fun main()
{
    val s1 = "İyi bir Android programcısı olmak için çok çalışmak gerekir. Çok çok çalışmak gerekir"
    val s2 = "Çok"

    println(s1.indexOf(s2))
    println(s1.indexOf(s2, 66))
    println(s1.indexOf(s2, ignoreCase = true))
}
```

>*String sınıfının lastIndexOf fonksiyonları*

```kotlin
package org.csystem.app;

fun main()
{
    val s1 = "İyi bir Android programcısı olmak için çok çalışmak gerekir. Çok çok çalışmak gerekir"
    val s2 = "çok"
    val index = s1.lastIndexOf(s2)

    println(index)
}
```

>*Sınıf Çalışması: Parametresi ile aldığı iki yazıdan birincisi içerisinde ikincisinden kaç tane olduğunu döndüren countString isimli fonksiyonu ignoreCase parametresi de içerecek şekilde yazınız*

```kotlin
package org.csystem.app;

import kotlin.random.Random

fun main() = runCountStringTest()

fun runCountStringTest()
{
    while (true) {
        print("Input the first text:")
        val s1 = readln()

        if (s1 == "quit")
            break

        print("Input the second text:")
        val s2 = readln()

        val ignoreCase = Random.nextBoolean()

        println(if (ignoreCase) "case insensitive" else "case sensitive")
        println("Count:${countString(s1, s2, ignoreCase)}")
    }
}

fun countString(s1: String, s2: String, ignoreCase: Boolean = false) : Int
{
    var idx = -1
    var count = 0

    while (true) {
        idx = s1.indexOf(s2, idx + 1, ignoreCase)
        if (idx == -1)
            break
        ++count
    }

    return count
}
```

>*isBlank ve isEmpty fonksiyonları. isBlank fonksiyonu yazının tamamı boşluk (whitespace) karakterlerinden oluşuyorsa true döndürür. isEmpty fonksiyonu yerine s == "" karşılaştırması kullanılabilir*

```kotlin
package org.csystem.app

fun main()
{
    print("Bir yazı giriniz:")
    val s = readln()

    println(s.isBlank())
    println(s.isEmpty())
    println(s == "")
}
```

>*Kotlin'e ait bazı sınıflarda isXXX metotlarının isNotXXX versiyonları da bulundurulur. Okunabilirlik açısından "mantıksal değil operatörü" ile isXXX çağırmak yerine isNotXXX çağrılmalıdır*

```kotlin
package org.csystem.app

fun main()
{
    print("Bir yazı giriniz:")
    var s = readln()

    if (s.isNotBlank()) //if (!s.isBlank())
        s = s.uppercase()

    println(s)
}
```

>*String sınıfının indexer elemanı ile yazının bir indekteki karakter elde edilebilir. indexer elemanı [] operatör fonksiyonu gibi düşünülebilir. Operatör fonksiyonları ileride ele alınacaktır*

```kotlin
package org.csystem.app

fun main()
{
    print("Bir yazı giriniz:")
    val s = readln()

    for (i in 0 until s.length)
        print("${s[i]} ")

    println()
}
```

>*String sınıfının tüm karakterleri for döngüsü ile elde edilebilir. Yani String sınıfı "iterable"'dır. Iterable kavramı ileride ele alınacaktır*

```kotlin
package org.csystem.app

fun main()
{
    print("Bir yazı giriniz:")
    val s = readln()

    for (ch in s)
        print("$ch ")

    println()
}
```

>*Aşağıdaki örnekte getRandomTextEN fonksiyonu içerisindeki döngüde count tane String nesnesi yaratılacak ve sürekli referans nesneden kopartıldığından (çünkü String  immutable bir sınıf) son yaratılan nesne nin referansı kullanılacaktır. Bu durumda daha önce yaratılan nesneler için "yaratılma maliyeti" söz konusu olabilecektir*

```kotlin
package org.csystem.app

import kotlin.random.Random

fun main() = runGetRandomTextENTest()

fun runGetRandomTextENTest()
{
    while (true) {
        print("Input count:")
        val count = readln().toInt()

        if (count <= 0)
            break

        val text = getRandomTextEN(count)

        println(text)
    }
}

fun getRandomTextEN(count: Int, random: Random = Random) : String
{
    var str = ""

    for (i in 1..count)
        str += (if (random.nextBoolean()) 'A' else 'a') + random.nextInt(26)

    return str;
}
```

>*Anahtar Notlar: Yazılarla işlem yapan, mutable olan ve String sınıfına yardımcı iki tane temel sınıf bulunmaktadır:<br>`StringBuilder, StringBuffer`.<br>Bu sınıflar genel olarak aynıdır. Aralarındaki farklar ileride ele alınacaktır. Farkların sözkonusu olduğu koldar dışında StringBuilder sınıfı kullanılmalıdır. Bu sınıflar içerisinde Char türden dizi tutulduğundan yazı üzerinde değişiklik yapılabilmektedir. Bu sınıflar genel olarak String sınıfının immutable olmasının dezavantajlı olduğu durumlarda kullanılır. StringBuffer sınıfının farkı ileride ele alınacaktır*

>*Aşağıdaki örnekte StringBuilder kullanılarak nesne yaratma maliyeti görece ortadan kaldırılmıştır*

```kotlin
package org.csystem.app

import kotlin.random.Random

fun main() = runGetRandomTextENTest()

fun runGetRandomTextENTest()
{
    while (true) {
        print("Input count:")
        val count = readln().toInt()

        if (count <= 0)
            break

        val text = getRandomTextEN(count)

        println(text)
    }
}

fun getRandomTextEN(count: Int, random: Random = Random) : String
{
    val sb = StringBuilder(count)

    for (i in 1..count)
        sb.append((if (random.nextBoolean()) 'A' else 'a') + random.nextInt(26))

    return sb.toString()
}
```

>*Aşağıdaki örneği inceleyiniz. StringBuilder kullanılmasaydı ne olurdu?*

```kotlin
package org.csystem.app

fun main() = runReverseTest()

fun runReverseTest()
{
    while (true) {
        print("Input text:")
        val s = readln()

        if (s == "quit")
            break

        val str = reverse(s)

        println("($str)")
    }
}

fun reverse(s: String) = StringBuilder(s).reverse().toString()
```

>*Char türünün bazı isXXX metotları*

```kotlin
package org.csystem.app

fun main()
{
    print("Bir karakter giriniz:")
    val ch = readln()[0]

    println(if (ch.isDigit()) "rakam" else "rakam değil")
    println(if (ch.isWhitespace()) "boşluk" else "boşluk değil")
    println(if (ch.isLetter()) "alfabetik" else "alfabetik değil")
}
```

>*Char türünün upperCase metodu. Bu ve lowercase metodu String'e geri döner. Char dondüren versiyonları için upperCaseChar ve lowerCaseChar metotları kullanılmalıdır*

```kotlin
package org.csystem.app

fun main()
{
    print("Bir karakter giriniz:")
    val ch = readln()[0]

    println(ch.uppercase())
}
```

>*Sınıf Çalışması: Parametresi ile aldığı bir yazının büyük harfleri küçük, küçük harleri büyük harf yapılmış ve geri kalan karakteler aynı olacak şekilde yeni bir yazıya geri dönen changeCase isimli fonksiyonu yazınız ve aşağıdaki kod ile test ediniz*

```kotlin
package org.csystem.app

fun main() = runChangeCaseTest()

fun runChangeCaseTest()
{
    while (true) {
        print("Input text:")
        val s = readln()

        if (s == "quit")
            break

        val str = changeCase(s)

        println("($str)")
    }
}

fun changeCase(s: String) : String
{
    val sb = StringBuilder(s)

    for (i in s.indices)
        sb[i] = if (s[i].isUpperCase()) s[i].lowercaseChar()else s[i].uppercaseChar()

    return sb.toString()
}
```

>*Sınıf Çalışması: Parametresi ile aldığı bir yazının büyük harfleri küçük, küçük harleri büyük harf yapılmış ve geri kalan karakteler aynı olacak şekilde yeni bir yazıya geri dönen changeCase isimli fonksiyonu yazınız ve aşağıdaki kod ile test ediniz*

```kotlin
package org.csystem.app

fun main() = runChangeCaseTest()

fun runChangeCaseTest()
{
    while (true) {
        print("Input text:")
        val s = readln()

        if (s == "quit")
            break

        val str = changeCase(s)

        println("($str)")
    }
}

fun changeCase(s: String) : String
{
    val sb = StringBuilder(s)

    for (i in s.indices)
        sb[i] = when {s[i].isUpperCase() -> s[i].lowercaseChar() else -> s[i].uppercaseChar()}

    return sb.toString()
}
```

>*Sınıf Çalışması: Parametresi ile aldığı bir yazının büyük harfleri küçük, küçük harfleri büyük harf yapılmış ve geri kalan karakteler aynı olacak şekilde yeni bir yazıya geri dönen changeCase isimli fonksiyonu yazınız ve aşağıdaki kod ile test ediniz*

```kotlin
package org.csystem.app

fun main() = runChangeCaseTest()

fun runChangeCaseTest()
{
    while (true) {
        print("Input text:")
        val s = readln()

        if (s == "quit")
            break

        val str = changeCase(s)

        println("($str)")
    }
}

fun changeCase(s: String) : String
{
    val sb = StringBuilder(s.length)

    for (c in s)
        if (c.isUpperCase())
            sb.append(c.lowercaseChar())
        else
            sb.append(c.uppercaseChar())

    return sb.toString()
}
```

>*Sınıf Çalışması: Parametresi ile aldığı bir yazının büyük harfleri küçük, küçük harleri büyük harf yapılmış ve geri kalan karakteler aynı olacak şekilde yeni bir yazıya geri dönen changeCase isimli fonksiyonu yazınız ve aşağıdaki kod ile test ediniz*

```kotlin
package org.csystem.app

fun main() = runChangeCaseTest()

fun runChangeCaseTest()
{
    while (true) {
        print("Input text:")
        val s = readln()

        if (s == "quit")
            break

        val str = changeCase(s)

        println("($str)")
    }
}
fun changeCase(s: String) : String
{
    val sb = StringBuilder(s.length)

    for (c in s)
        when {
            c.isUpperCase() -> sb.append(c.lowercaseChar())
            else -> sb.append(c.uppercaseChar())
        }

    return sb.toString()
}
```

>*String sınıfının substring metotları*

```kotlin
package org.csystem.app

fun main()
{
    val s = "ankara"

    println(s.substring(2))
    println(s.substring(2, 5)) //[2, 5)
    println(s.substring(2..4)) //[2, 4]
    println(s.substring(2 until 5)) //[2, 5)
}
```

>*Sınıf Çalışması: Parametresi ile aldığı bir yazının baş harfini büyük geri kalan harflerini küçük yapan capitalize isimli fonksiyonu yazınız ve test ediniz<br>Örnek: profesyonel Bir Android Programcısı olmak için çok çalışmak gerekir -> Profesyonel bir android programcısı olmak için çok çalışmak gerekir*

```kotlin
package org.csystem.app

fun main() = runCapitalizeTest()

fun runCapitalizeTest()
{
    while (true) {
        print("Bir yazı giriniz:")
        val text = readln()

        println(capitalize(text))

        if (text == "elma")
            break
    }

    println("Tekrar yapıyor musunuz?")
}

fun capitalize(s: String) = if (s.isNotEmpty()) s[0].uppercase() + s.substring(1).lowercase() else ""
```

>*String sınıfının substringBefore ve substringAfter metotları*

```kotlin
package org.csystem.app

fun main()
{
    val s = "ankara-istanbul-izmir"

    println(s.substringAfter('-'))
    println(s.substringBefore('-'))
}
```

>*String sınıfının substringBefore ve substringAfter metotlarının  missingDelimiterValue parametreleri String türdendir. Ayraç bulunamazsa default olarak yazının kendisini döner. Programcı bu parametre için, ayraç bulamadığında döneceği yazıyı argüman olarak geçebilir*

```kotlin
package org.csystem.app

fun main()
{
    print("Bir yazı giriniz:")
    val s = readln()

    println(s.substringAfter('-', "zonguldak"))
    println(s.substringBefore('-', "izmir"))
}
```

>*String sınıfının substringBefore ve substringAfter metotları ayraç olarak bir String alabilirler*

```kotlin
package org.csystem.app

fun main()
{
    val s = "ankara-istanbul-;izmir"

    println(s.substringAfter("-;"))
    println(s.substringBefore("-;"))
}
```

>*String sınıfının substringAfterLast ve substringBeforeLast metotları*

```kotlin
package org.csystem.app

fun main()
{
    print("Bir yazı giriniz:")
    val s = readln()

    println(s.substringAfterLast('-'))
    println(s.substringBeforeLast('-'))
}
```

>*String sınıfının substringAfterXXX ve substringBeforeXXX metotlarının kullanımı*

```kotlin
package org.csystem.app

fun main()
{
    print("Email adresinizi giriniz:")
    val s = readln()
    val info = s.substringBefore('@', "Geçersiz email ismi")
    val domain = s.substringAfter('@', "Geçersiz email domain'i")
    val extension = s.substringAfterLast('.', "Geçersiz uzantı")

    println("Info:${info}")
    println("Domain:${domain}")
    println("Extension:${extension}")
}
```

>*String sınıfının substringAfterLast metodunun kullanımı*

```kotlin
package org.csystem.app

fun main()
{
    print("Dosya yol bilgisini giriniz:")
    val path = readln()
    val fileName = path.substringAfterLast('/');

    println(fileName)
}
```

>*String template*

```kotlin
package org.csystem.app

fun main()
{
    val fahrenheit: Double = 60.0
    val s = "Bugün hava ${5.0 / 9.0 * (fahrenheit - 32)} derece"

    println(s)
}
```

>*İki tırnak içerisinde escape sequence kullanımı*

```kotlin
package org.csystem.app

fun main()
{
    val s = "c:\\test\\numbers.txt"

    println(s)
}
```

>*""" ve """ arasındaki String'lere raw (regular) string denir. Bu string sabitlerinde escape sequence karakterler
kullanılamaz*

```kotlin
package org.csystem.app

fun main()
{
    val s = """c:\test\numbers.txt"""

    println(s)
}
```

>*İki tırnak arasında bulunan String sabitleri aynı satırda yazılmalıdır. Aksi durumda error oluşur*

```kotlin
package org.csystem.app

fun main()
{
    val s = "c:\test\numbers.txt
            ali" //error

    println(s)
}
```

>*Yukarıdaki problem raw string kullanılarak çözülebilir. Raw stringlerde tüm karakterler kendi anlamındadır. WYSIWYG (What You See Is What You Get)*

```kotlin
package org.csystem.app

fun main()
{
    val s = """c:\test\numbers.txt
                ali"""

    println(s)
}
```

>*Raw string'lerde iki tırnak karakteri tek başına kullanılabilir*

```kotlin
package org.csystem.app

fun main()
{
    val s = """"ankara"""

    println(s)
}
```

>*Raw string'lerde tek tırnak karakteri tek başına kullanılabilir*

```kotlin
package org.csystem.app

fun main()
{
    val s = """'ankara'"""

    println(s)
}
```

>*Raw string içerisinde $ karakteri ile ifade yazımı (string template) yapılabilir*

```kotlin
package org.csystem.app

fun main()
{
    val a = 10
    val s = """a = $a"""

    println(s)
}
```

>*String sınıfının format metodu Java'daki format metoduna benzer*

```kotlin
package org.csystem.app

fun main()
{
    print("Bir sayı giriniz:")
    val value = readln().toInt()
    val s = "value = %X".format(value)

    println(s)
}
```

>*String sınıfının format metodu Java'daki format metoduna benzer:*

    Bazı format karakterleri:
    d       -> decimal tamsayı türü
    x, X    -> hexadecimal tamsayı türü
    o       -> octal tamsayı türü
    c       -> Char
    f       -> Float veya Double
    s       -> String
    b       -> Boolean

```kotlin
package org.csystem.app

fun main()
{
    val x = 100
    val y = 50
    val s = "(%03d, %03d)".format(x, y)

    println(s)
}
```

>*String sınıfının format metodunda format karakterlerinin kullanımı*

```kotlin
package org.csystem.app

fun main()
{
    print("Birinci sayıyı giriniz:")
    val a = readln().toDouble()

    print("İkinci sayıyı giriniz:")
    val b = readln().toDouble()

    val c = a + b

    println("%f + %f = %.20f".format(a, b, c))
}
```

>*String sınıfının format metodunda format karakterlerinin kullanımı*

```kotlin
package org.csystem.app

fun main()
{
    var day = 11
    var mon = 7
    var year = 1983
    var hour = 12
    var minute = 9
    var second = 0

    println("%02d/%02d/%04d %02d:%02d:%02d".format(day, mon, year, hour, minute, second))
}
```

>*Aşağıda anlatılan import bildirimlerinde kullanılan "yıldızlı import bildirimi" ve "yıldızsız impoprt bildirimi" terimleri sırasıyla "import on demand declaration" ve "import single type/name declaration" anlamında kullanılmaktadır. Türkçe karşılıkları tamamen Oğuz Karan tarafından uydurulmuştur*

>*Yıldızsız import bildiriminde (import single type declaration) ilgili isme takma isim (alias) verilebilir*

```kotlin
package org.csystem.app

import org.csystem.util.console.kotlin.readInt as rInt

fun main()
{
    val a = rInt("Bir sayı giriniz:")

    println("a = $a")
}
```

>*Yıldızsız import bildiriminde ilgili isme takma isim (alias) verilebilir. Aşağıdaki örneği inceleyiniz*

```kotlin
package org.csystem.app

package org.csystem.app

import test.foo as tFoo
import mest.foo as mFoo
import test.bar

fun main()
{
    tFoo()
    bar()
    mFoo()
}

package test

fun foo()
{
    println("test.foo")
}

fun bar()
{
    println("test.bar")
}

package mest

fun foo()
{
    println("mest.foo")
}
```

>*Yıldızsız import bildiriminde ilgili isme takma isim (alias) verilebilir*

```kotlin
package org.csystem.app

import test.bar
import test.foo
import mest.foo as mFoo

fun main()
{
    foo()
    bar()
    mFoo()
}

package test

fun foo()
{
    println("test.foo")
}

fun bar()
{
    println("test.bar")
}

package mest

fun foo()
{
    println("mest.foo")
}
```

>***Sınıf Çalışması:** Parametresi ile aldığı bir yazının pangram olup olmadığını test eden isPangramT ve isPangramEN fonksiyonlarını yazınız ve aşağıdaki kod ile test ediniz*\
***Açıklama:** Fonksiyonlar özel isim ve cümle anlamı kontrolü yapmayacaktır*\
***Türkçe pangram**      : Pijamalı hasta yağız şoföre çabucak güvendi*\
***İngilizce pangram**   : The quick brown fox jumps over the lazy dog*

```kotlin
package org.csystem.util.string.kotlin.test

import org.csystem.util.string.kotlin.isPangramEN
import org.csystem.util.string.kotlin.isPangramTR

fun main() = runIsPangramTest()

fun runIsPangramTest()
{
    runIsPangramTRTest()
    runIsPangramENTest()
}

fun runIsPangramTRTest()
{
    while (true) {
        print("Bir yazı giriniz:")
        val s = readln()

        if ("elma" == s)
            break

        println(if (isPangramTR(s)) "Pangram" else "Pangram değil")
    }
}

fun runIsPangramENTest()
{
    while (true) {
        print("Input a text:")
        val s = readln()

        if ("quit" == s)
            break

        println(if (isPangramEN(s)) "Pangram" else "Not a Pangram")
    }
}
```

>***Sınıf Çalışması:** Parametresi ile aldığı bir yazının pangram olup olmadığını test eden isPangramTR ve isPangramEN fonksiyonlarını yazınız ve aşağıdaki kod ile test ediniz*\
>***Açıklama:** Fonksiyonlar özel isim ve cümle anlamı kontrolü yapmayacaktır* \
>***Türkçe pangram      :** Pijamalı hasta yağız şoföre çabucak güvendi* \
>***İngilizce pangram   :** The quick brown fox jumps over the lazy dog*

```kotlin
package org.csystem.util.string.kotlin.test

import org.csystem.util.string.kotlin.isPangramEN
import org.csystem.util.string.kotlin.isPangramTR

fun main() = runIsPangramTest()

fun runIsPangramTest()
{
    runIsPangramTRTest()
    runIsPangramENTest()
}

fun runIsPangramTRTest()
{
    while (true) {
        print("Bir yazı giriniz:")
        val s = readln()

        if ("elma" == s)
            break

        println(if (isPangramTR(s)) "Pangram" else "Pangram değil")
    }
}

fun runIsPangramENTest()
{
    while (true) {
        print("Input a text:")
        val s = readln()

        if ("quit" == s)
            break

        println(if (isPangramEN(s)) "Pangram" else "Not a Pangram")
    }
}
```

>***Sınıf Çalışması**:Parametresi ile aldığı bir yazının içerisindeki ilk en uzun palindromu döndüren getFirstLongestPalindrome ve son en uzun parlindromu döndüren getLastLongestPalindrome fonksiyonlarını ve aynı fonksiyonların en kısa palindromları döndüren getFirstShortestPalindrome ve getLastShortestPalindrome fonksiyonlarını yazınız. En kısa palindrom en az iki karakterden oluşmalıdır.*\
>***Palindrome:** İçerisinde bulunan yalnızca alfabetik karakterler tersten okunduğunda aynısı olaran yazılardır:*\
>***Örnek:***\
Ey Edip Adana'da pide ye\
Anastas mum satsana\
Ali Papila\
***İpucu:** Parametresi ile alığı biri yazının paklindrome olup olmadığını test eden örneğin isPalindrome isimli bir fonksiyon yazabilirsiniz*

>***Sınıf Çalışması:** Parametresi ile aldığı bir yazının isogram olup olmadığını test eden isIsogramTR ve isIsogramEN fonksiyonlarını stringUtil.kt içerisinde yazınız ve aşağıdaki kod ile test ediniz*\
> ***Isogram:** Yazı içerisinde ilgili alfabenin her karakterinden yalnızca bir tane bulunan yazılara denir*\
> ***Not:** İleride daha iyisi yazılacaktır*

```kotlin
package org.csystem.util.string.kotlin.test

import org.csystem.util.console.kotlin.readString
import org.csystem.util.string.kotlin.isIsogramEN
import org.csystem.util.string.kotlin.isIsogramTR

fun main() = runIsIsogramTest()

fun runIsIsogramTest()
{
    runIsIsogramTRTest()
    runIsIsogramENTest()
    println("Tekrar yapıyor musunuz?")
}

fun runIsIsogramTRTest()
{
    while (true) {
        val s = readString("Bir yazı giriniz:")
        if ("elma" == s)
            break

        println(if (isIsogramTR(s)) "Isogram" else "Isogram değil")
    }
}

fun runIsIsogramENTest()
{
    while (true) {
        val s = readString("Enter a text:")
        if ("quit" == s)
            break
        println(if (isIsogramEN(s)) "Isogram" else "Not an isogram")
    }
}
```

***Anahtar Notlar:*** Kotlin dosyaları için tipik IDE programlar (IntelliJ Idea, Android Studio, Eclipse vb.) Java dosyalarında olduğu gibi hangi paket içerisinde bulunuyorsa o dizinde bulunur zorunluluğunu uygulamaz. Ancak Kotlin JVM kullanan programcılar genelde Java'daki gibi dosyaları konumlandırırlar

>*arrayOf generic fonksiyonu ile bir dizi ilk değer verme sentaksı biçiminde kullanılabilir. Diziler Kotlin'de de dolaşılabilir (iterable) olduğundan for döngü deyimi kullanılabilir*

```kotlin
package org.csystem.app

fun main()
{
    val cities = arrayOf("ankara", "istanbul", "izmir")

    for (city in cities)
        println(city)
}
```

>*Dizilerin eleman sayısına size property elemanı ile erişilebilir*

```kotlin
package org.csystem.app

fun main()
{
    val cities = arrayOf("ankara", "istanbul", "izmir")
    val size = cities.size
    var i = 0

    while (i < size) {
        println(cities[i])
        ++i
    }
}
```

>*Dizilerin eleman sayısı size property elemanı ile elde edilebilir*

```kotlin
package org.csystem.app

fun main()
{
    val cities = arrayOf("ankara", "istanbul", "izmir")
    val size = cities.size

    for (i in 0 until size)
        println(cities[i])
}
```

>*Dizilerin eleman sayısı count isimli extension fonksiyon ile de elde edilebilir*

```kotlin
package org.csystem.app

fun main()
{
    val cities = arrayOf("ankara", "istanbul", "izmir")
    val n = cities.count()

    for (i in 0 until n)
        println(cities[i])
}
```

>*Int türden dizi*

```kotlin
package org.csystem.app

fun main()
{
    val numbers = arrayOf(10, 20, 30)

    for (value in numbers)
        print("$value ")

    println()
}
```

>*Dizilerin indices property elemanı `[0, size)` aralığında bir IntRange referansına döner:*\
>*Eşdeğer bir döngü:*

```kotlin
for (i in 0 until a.size)
        a[i] *= a[i]
```

```kotlin
package org.csystem.app

fun main()
{
    val a = arrayOf(10, 20, 30)

    for (i in a.indices)
        a[i] *= a[i];

    for (value in a)
        print("$value ")

    println()
}
```

>***Sınıf Çalışması:** Parametresi ile aldığı Int türden bir dizinin elemanlarını stdout'a yazdıran write isimli fonksiyonu ve aldığı Int türden count, min ve bound ile random isimli Random türden parametreleri count elemanlı elemanları `[min, bound)` aralığında rasgele değerlerle doldurulmuş bir dizi referansına geri dönen randomIntArray isimli fonksiyonu arrayUtil.kt içerisinde yazınız ve aşağıdaki kod ile test ediniz*

```kotlin
package org.csystem.util.array.kotlin.test

import org.csystem.util.array.kotlin.randomIntArray
import org.csystem.util.array.kotlin.write
import org.csystem.util.console.kotlin.readInt

fun main() = runRandomIntArrayTest()

fun runRandomIntArrayTest()
{
    while (true) {
        val count = readInt("Dizinin eleman sayısını giriniz:")

        if (count <= 0)
            break
        val a = randomIntArray(count, 0, 100)

        write(2, a)
    }

    println("Tekrar yapıyor musunuz?")
}
```


>*joinToString fonksiyonu ile dizi içerisindeki elemanlar bir ayraç veya bir ön ek veya bir son ek ile birleştirilebilir*

```kotlin
package org.csystem.app

import org.csystem.kotlin.util.array.randomIntArray

fun main()
{
    val a = randomIntArray(10, 0, 100)

    write(2, a)

    val str = a.joinToString("-", "{", "}")

    println(str)
}
```

>*joinToString fonksiyonu ile dizi içerisindeki elemanlar bir ayraç veya bir ön ek veya bir son ek ile birleştirilebilir*

```kotlin
package org.csystem.app

import org.csystem.util.array.kotlin.randomIntArray
import org.csystem.util.array.kotlin.write

fun main()
{
    val a = randomIntArray(10, 0, 100)

    write(2, a)

    val str = a.joinToString(prefix = "{", separator = "-", postfix = "}")

    println(str)
}
```

>*joinToString fonksiyonu*

```kotlin
package org.csystem.app

import org.csystem.util.array.kotlin.randomIntArray
import org.csystem.util.array.kotlin.write

fun main()
{
    val a = randomIntArray(10, 0, 100)

    write(2, a)

    val str = a.joinToString(prefix = "{", postfix = "}")

    println(str)
}
```

>***Sınıf Çalışması:** Parametresi ile aldığı Long türden bir sayının basamaklarından oluşan diziyi döndüren digits fonksiyonunu yazınız ve aşağıdaki kod ile test ediniz*\
> ***Not:** Sayı negatif olsa bile basamak değerleri pozitif olacaktır*

```kotlin
package org.csystem.util.numeric.test

import org.csystem.util.array.kotlin.write
import org.csystem.util.console.kotlin.readInt
import org.csystem.util.numeric.digits
import kotlin.random.Random

fun main() = runDigitsLongTest()

fun runDigitsLongTest()
{
    val count = readInt("Bir sayı giriniz:")

    for (i in 1..count) {
        val value = Random.nextLong();
        print("$value -> ")
        write(digits(value))
    }

    println("Tekrar yapıyor musunuz?")
}
```

>***Sınıf Çalışması:** Parametresi ile aldığı Int türden bir sayının basamaklarından oluşan diziyi döndüren digits fonksiyonunu yazınız ve aşağıdaki kod ile test ediniz*\
>***Not:** Sayı negatif olsa bile basamak değerleri pozitif olacaktır*

```kotlin
package org.csystem.util.numeric.test

import org.csystem.util.array.kotlin.write
import org.csystem.util.console.kotlin.readInt
import org.csystem.util.numeric.digits
import kotlin.random.Random

fun main() = runDigitsIntTest()

fun runDigitsIntTest()
{
    val count = readInt("Bir sayı giriniz:")

    for (i in 1..count) {
        val value = Random.nextInt();
        print("$value -> ")
        write(digits(value))
    }

    println("Tekrar yapıyor musunuz?")
}
```

>***Sınıf Çalışması:** Parametresi ile aldığı 1, 2 veya 3 basamaklı bir sayının Türkçe yazı karşılığını döndüren numToStr3DigitsTR fonksiyonunu yazınız ve aşağıdaki kod ile test ediniz.*
>
>***Açıklamalar:***
>- Fonksiyon basamak sayısı kontrolü yapmayacaktır
>- Bu fonksiyonun daha geneli yazıldığında private olarak bildirilecektir. Şimdilik bunu görmezden geliniz

```kotlin
package org.csystem.util.numeric.test

import org.csystem.util.console.kotlin.readInt
import org.csystem.util.numeric.numToStr3DigitsTR
import kotlin.random.Random

fun main() = runNumToStrTest()

fun runNumToStrTest()
{
    val count = readInt("Bir sayı giriniz:")

    for (i in 1..count) {
        val value = Random.nextInt(-999, 1000)
        println("$value -> ${numToStr3DigitsTR(value)}")
    }

    println("Tekrar yapıyor musunuz?")
}
```

>***Sınıf Çalışması:** Parametresi ile aldığı Long türden bir sayıyı 3'erli basamaklara ayırarak bir diziye yerleştiren ve dizinin referansını döndüren digitsInThrees fonksiyonunu yazınız ve aşağıdaki kod ile test ediniz*
>
> ***Açıklama:** Negatif sayılar için de dizinin elemanları pozitif olacaktır*\
> 
```
1234567 -> 1 234 567
1       -> 1
3456    -> 3 456
12456   -> 12 456
123456  -> 123 456
1000000 -> 1 0 0
```

>***Sınıf Çalışması:** Parametresi ile aldığı Int türden bir sayıyı 3'erli basamaklara ayırarak bir diziye yerleştiren ve dizinin referansını döndüren digitsInThrees fonksiyonunu yazınız ve aşağıdaki kod ile test ediniz*
>
>***Açıklama:** Negatif sayılar için de dizinin elemanları pozitif olacaktır*
```
1234567 -> 1 234 567
1       -> 1
3456    -> 3 456
12456   -> 12 456
123456  -> 123 456
1000000 -> 1 0 0
```

>***Sınıf Çalışması:** Parametresi ile aldığı gün, ay ve yıl bilgisine ilişkin tarihin haftanın hangi gününe geldiğini döndüren getDayOfWeek global fonksiyonunu aşağıdaki açıklamalara uygun olarak yazınız.*

>***Açıklama:***\
-Aşağıdaki test kodu ile fonksiyonlarınız için genel bir test yapınız\
-Programda tarih zamana ilişkin sınıflar kullanılmayacaktır.\
-getDayOfWeek fonksiyonu 1.1.1900 tarihinden sonraki tarihler için çalışacaktır.\
-Fonksiyonlar geçersiz bir tarih için -1 değerini döndürecektir\
-Haftanın günü bilgisi, 1.1.1900 ile verilen tarih arasındaki toplam gün sayısı hesaplanıp 7 değerine
göre modu alınarak bulunabilir. Bu değere göre sıfır pazar, 1 pazartesi, ..., 6 değeri de Cumartesi gününe karşılık gelir\
-Programda dizi kullanılmayacaktır\
-Aşağıdaki fonksiyonların kesinlikle yazılması koşuluyla istediğiniz fonksiyonu ekleyebilirsiniz.\
-Yazılmış fonksiyonlar içerisinde değişiklik yapabilirsiniz. Ancak test etmeniz gerektiğini unutmayınız\
-Çözüm şu ana kadar gördüğümüz konular kullanılarak yapılacaktır\
-String referansına geri dönen fonksiyonlarda String sınıfını kullanmanız gerekmez. String literal oluştururakyapınız\

(İleride daha iyisi yazılacaktır)

```kotlin
package org.csystem.app

import org.csystem.util.console.kotlin.readInt

fun main() = runtTest();

fun runtTest()
{
    while (true) {
        val day = readInt("Gün?")

        if (day <= 0)
            break


        val month = readInt("Ay?")
        val year = readInt("Yıl?")

        displayDateTR(day, month, year)
        displayDateEN(day, month, year)
    }

    println("Tekrar yapıyor musunuz?")
}


fun displayDateTR(day: Int, month: Int, year: Int)
{
    val dayOfWeek = getDayOfWeek(day, month, year)

    println(when {
        dayOfWeek >= 0 -> "$day ${getMonthNameTR(month)} $year ${getDayOfWeekTR(dayOfWeek)}"
        else -> "Geçersiz Tarih"
    })
}

fun displayDateEN(day: Int, month: Int, year: Int)
{
    val dayOfWeek = getDayOfWeek(day, month, year)

    println(when {
        dayOfWeek >= 0 -> "$day${getDaySuffix(day)} ${getMonthNameEN(month)} $year ${getDayOfWeekEN(dayOfWeek)}"
        else -> "Invalid Date"
    })
}

fun getDaySuffix(day: Int) :String
{
    return when (day) {
        1, 21, 31 -> "st"
        2, 22 -> "nd"
        3, 23 -> "rd"
        else -> "th"
    }
}

fun getDayOfWeekTR(dayOfWeek: Int) : String
{
    return when (dayOfWeek) {
        0 -> "Pazar"
        1 -> "Pazatesi"
        2 -> "Salı"
        3 -> "Çarşamba"
        4 -> "Perşembe"
        5 -> "Cuma"
        else -> "Cumartesi"
    }
}

fun getMonthNameTR(month: Int) : String
{
    return when (month) {
        1 -> "Ocak"
        2 -> "Şubat"
        3 -> "Mart"
        4 -> "Nisan"
        5 -> "Mayıs"
        6 -> "Haziran"
        7 -> "Temmuz"
        8 -> "Ağustos"
        9 -> "Eylül"
        10 -> "Ekim"
        11 -> "Kasım"
        else -> "Aralık"
    }
}

fun getDayOfWeekEN(dayOfWeek: Int) : String
{
    return when (dayOfWeek) {
        0 -> "Sun"
        1 -> "Mon"
        2 -> "Tue"
        3 -> "Wed"
        4 -> "Thu"
        5 -> "Fri"
        else -> "Sat"
    }
}

fun getMonthNameEN(month: Int) : String
{
    return when (month) {
        1 -> "Jan"
        2 -> "Feb"
        3 -> "Mar"
        4 -> "Apr"
        5 -> "May"
        6 -> "Jun"
        7 -> "Jul"
        8 -> "Aug"
        9 -> "Sep"
        10 -> "Oct"
        111 -> "Nov"
        else -> "Dec"
    }
}

fun getDayOfWeek(day: Int, month: Int, year: Int) : Int
{
    if (year < 1900)
        return -1

    val totalDays = getDayOfYear(day, month, year)

    if (totalDays == -1)
        return -1

    return (totalDays + getTotalDays(year)) % 7
}

fun getTotalDays(year: Int) : Int
{
    var totalDays = 0

    for (y in 1900 until year)
        totalDays += if (isLeapYear(y)) 366 else 365

    return totalDays
}

fun getDayOfYear(day: Int, month: Int, year: Int) : Int
{
    if (!isValidDate(day, month, year))
        return -1

    var dayOfYear = day

    for (m in (month - 1) downTo 1)
        dayOfYear += getDaysOfMonth(m, year)

    return dayOfYear
}

fun getDaysOfMonth(month: Int, year: Int) : Int
{
    return when (month) {
        4, 6, 9, 11 -> 30
        2 -> if (isLeapYear(year)) 29 else 28
        else -> 31
    }
}

fun isValidDate(day: Int, month: Int, year: Int) = day in 1..31 && month in 1..12 && day <= getDaysOfMonth(month, year)

fun isLeapYear(year: Int) = year % 4 == 0 && year % 100 != 0 || year % 400 == 0
```

**Kotlin'de erişim belirleyiciler iki kategoriye ayrılırlar:**

**Global düzeyde erişim belirleyiciler:**
- Hiçbir şey yazmamak default erişimdir. public'dir
- public: Bir global elemanın dosyası dışından da erişilebilmesi demektir
- internal: Module düzeyinde erişimdir. İleride ele alınacak
- private: Yalnızca kendi dosyası içerisinde erişilebilirdir

**Sınıf elemanlarının erişim belirleyicileri:**
- Hiçbir şey yazmamak default erişimdir. public'dir
- public: Sınıf dışından da erişilebilirdir
- internal: Modül düzeyinde erişimdir
- protected: Yalnızca türemiş sınıflar erişebilir
- private: Yalnızca kendi sınıfı içerisinde erişilebilir elemanlardır

>*Kotlin'de Java'da ki gibi pakete özgü (package private) erişim belirleyiciler yoktur. Kotlin'de bu durum modül (module) düzetinde ele alınmıştır. Module kavramı ileride ele alınacaktır*

>*Kotlin'deki enum türü Java'dakine çok benzer.*

***Anahtar Notlar:*** enum kullanımı Android programlamada bazı durumlarda performası olumsuz yönde etkileyebilir. Bu tip durumlar ileride ela alınacaktır

>*enum türleri enum ve class anahtar sözcükleri ile bildirilir. enum sabitleri (enum constants) ait oldukları enum türünden referanslardır. Şüphesiz bu referanslar da aynı enum türünden yaratılmış olan nesnelerin adreslerini tutarlar*

```kotlin
package org.csystem.app

fun main()
{
    val favColor: Color = Color.BLUE

    println(favColor.toString())
}

enum class Color {
    RED, GREEN, BLUE
}
```

>*enum class türünden nesne hiç bir şekilde yaratılamaz. Sabitlere ilişkin nesneler zaten yaratılmıştır. Bu anlamda bir enum class'ın ctor'ları private'dan öte bir erişime sahiptir*

```kotlin
package org.csystem.app

fun main()
{
    val favColor = Color() //error

    println(favColor.toString())
}


enum class Color {
    RED, GREEN, BLUE
}
```

>*enum türlerinin ordinal property elemanı o enum referansına ilişkin sabitin bildirim sıra numarasını verir. ordinal numarası sıfırdan başlar*

```kotlin
package org.csystem.app

fun main()
{
    val favColor = Color.GREEN
    val ordinal = favColor.ordinal

    println("ordinal of $favColor is $ordinal")
}

enum class Color {
    RED, GREEN, BLUE
}
```

>*enum türlerinin values metodu tüm enum sabitlerine ilişkin referanslardan oluşan dizi referansı döndürür*

```kotlin
package org.csystem.app

fun main()
{
    val ordinal = 2
    val color = Color.values()[ordinal]

    println(color)
}


enum class Color {
    RED, GREEN, BLUE
}
```

>*enum türlerinin sabitlere ilişkin referanslar values isimli metot ile elde edilebilir*

```kotlin
package org.csystem.app

fun main()
{
    for (dayOfWeek in DayOfWeek.values())
        print("$dayOfWeek ")

    println()
}

enum class DayOfWeek {
    SUN, MON, TUE, WED, THU, FRI, SAT
}
```

>*enum türlerinin valueOf metodu parametresi ile aldığı yazıya ilişin bir enum sabiti varsa o sabitin tuttuğu adrese geri döner. Yoksa exception oluşur*

```kotlin
package org.csystem.app

import org.csystem.util.console.kotlin.readString

fun main()
{
    val s = readString("Input the day of week as string with three characters:")
    val dow = DayOfWeek.valueOf(s.uppercase())

    println(dow)
}


enum class DayOfWeek {
    SUN, MON, TUE, WED, THU, FRI, SAT
}
```

>*enum sabitlerine değer iliştirilmesi*

```kotlin
package org.csystem.app

import org.csystem.util.console.kotlin.readInt
import kotlin.random.Random

fun main()
{
    val colorValues = Color.values()
    val monthValues = Month.values()
    val count = readInt("Bir sayı giriniz:")

    for (i in 1..count) {
        val favColor = colorValues[Random.nextInt(colorValues.size)]
        val month = monthValues[Random.nextInt(monthValues.size)]

        println("-------------------------------------------------------")
        println("(R=${favColor.r}, G=${favColor.g}, B=${favColor.b})")
        println("${month.textTR}, ${month.days}")
        println("-------------------------------------------------------")
    }
}

enum class Month(val days: Int, val textTR: String) {
    JAN(31, "Ocak"), FEB(28, "Şubat"), MAR(31, "Mart"), APR(30, "Nisan"), MAY(31, "Mayıs"),  JUN(30, "Haziran"),
    JUL(31, "Temmuz"), AUG(31, "Ağustos"), SEP(30, "Eylül"), OCT(31, "Ekim"), NOV(30, "Kasım"), DEC(31, "Aralık")
}

enum class Color(val r: Int = 0, val g: Int = 0, val b: Int = 0) {
    RED(255), GREEN(g = 255), BLUE(b = 255), WHITE(255, 255, 255), BLACK
}
```

>*enum'ların eşitlik karşılaştırması `==` veya `===` operatörü ile yapılabilir*

```kotlin
package org.csystem.app

import org.csystem.util.console.kotlin.readInt
import kotlin.random.Random

private val g_colors = Color.values()

fun main() = runRandomColorGenerator()

fun runRandomColorGenerator()
{
    val count = readInt("Kaç tane renk üretmek istersiniz:")

    for (i in 1..count) {
        val c1 = getRandomColor()
        val c2 = getRandomColor()

        println("---------------------------------------------------------------")
        println("$c1: r = ${c1.r}, g = ${c1.g}, b = ${c1.b}")
        println("$c2: r = ${c2.r}, g = ${c2.g}, b = ${c2.b}")
        println(if (c1 === c2) "Aynı renk" else "Farklı renkler")
        println(if (c1 == c2) "Aynı renk" else "Farklı renkler")
        println("---------------------------------------------------------------")
    }
}

fun getRandomColor() = g_colors[Random.nextInt(g_colors.size)]

enum class Color(val r: Int = 0, val g: Int = 0, val b: Int = 0) {
    RED(255), GREEN(g = 255), BLUE(b = 255), WHITE(255, 255, 255), BLACK
}
```

>*enum'lara başka elemanlar da eklenebilmektedir. Aşağıdaki örneği inceleyiniz*

```kotlin
package org.csystem.app

import org.csystem.util.console.kotlin.readInt
import kotlin.random.Random

fun main()
{
    val count = readInt("Bir sayı giriniz:")
    val values = Month.values()
    val size = values.size

    for (i in 1..count) {
        val month = values[Random.nextInt(size)]
        val year = Random.nextInt(1999, 2150)

        println("${year}, ${month.textTR}, ${month.getDaysByYear(year)}")
    }
}

enum class Month(val days: Int, val textTR: String) {
    JAN(31, "Ocak"), FEB(28, "Şubat"), MAR(31, "Mart"), APR(30, "Nisan"), MAY(31, "Mayıs"),  JUN(30, "Haziran"),
    JUL(31, "Temmuz"), AUG(31, "Ağustos"), SEP(30, "Eylül"), OCT(31, "Ekim"), NOV(30, "Kasım"), DEC(31, "Aralık");
    companion object {
        private fun isLeapYear(year: Int) = year % 4 == 0 && year % 100 != 0 || year % 400 == 0
    }

    fun getDaysByYear(year: Int) = if (ordinal == 1 && isLeapYear(year)) 29 else days
}
```

>*enum sınıfları ile Java' da olduğu gibi Kotlin'de de Singleton bir sınıf yazılabilir. Kotlin'de Singleton sınıf yazmanın daha kolay bir yöntemi de ileride anlatılacaktır*

```kotlin
package org.csystem.app

fun main()
{
    val repository1 = DeviceRepository.INSTANCE
    val repository2 = DeviceRepository.INSTANCE

    println(if (repository1 === repository2) "Aynı nesne" else "Farklı nesneler")
}

enum class DeviceRepository {
    INSTANCE;
    //...
}
```

***Anahtar Notlar:*** Kotlin'de sınıfların private property elemanlarını Java'daki gibi `m_` ile değil, `m` öneki ile başlatacağız

#### Nesne yönelimli programlamanın temel ilkeleri:

- Single Responsibility Principle (SRP)
- Open Closed Principle (OCP)
- Liskov Substitution Principle (LSP)
- Interface Segregation Principle (ISP)
- Dependency Inversion Principle (DIP)

#### Sınıflararası ilişkiler:

Sınıflararası ilişkiler aslında nesneler arasındaki ilişkiler olarak düşünülmelidir. Örneğin araba ile motoru arasında bir ilişki vardır. Ya da insan ile kimlik kartı arasında da biri ilişki vardır. Bu ilişkiler aslında nesneler arasındadır. Ancak nesnelerin bu ilişkiler olacak şekilde yaratılabilmesi ve kullanılabilmesi için sınıfların uygun bir biçimde yazılması gerekir.

Nesne yönelimli programlama tekniği kullanılarak geliştirilecek bir projenin kodlama aşamasına gelindiğinde önce sınıflar ve aralarındaki ilişkiler belirlenir. Sonra kodlamaya geçilir. İlişkiler belirlenirken sınıfların ve nesnelerin konuya (domain) ilişkin durumları düşünülür.

***Anahtar Notlar:*** Bir projenin müşteri ile görüşülmesinden (ihtiyaçların belirlenmesi) teslimine (deployment) kadar geçen sürecin çeşitli şemalarla anlatılmasını sağlayan UML (Unified Modeling Language) denilen bir araç bazı durumlarda kullanılabilmektedir. Bu aracın önemli ve geliştiricileri ilgilendiren şemalarından birisi "sınıf şemaları (class diagrams)"'dır. Bu, kodlamaya yönelik ve kodlamaya başlamadan önce yapılan bir şemadır. Bu şemada sınıfların detayları ve aralarındaki ilişkiler çizilir. UML her zaman ve her detayıyla kullanılmalı mıdır? Bunun için verilebilecek cevap nettir: Hayır. Gerekiğinde ve gerektiği kadarı kullanılmalıdır.

***Anahtar Notlar:*** Kavram ya da kavramlar modellenirken genel durumlar düşünülür. İstisna niteliğinde olabilecek durumlar modelleme yapılırken - genel olarak - göz önünde bulundurulmaz. Aksi durumda hiçbir şey modellenemez. Örneğin, araba ile motoru arasındaki ilişki için şu durumlar söyleniyor olsun:
1. Araba nesnesine ait olan motor nesnesi başka bir araba nesnesi veya başka bir nesne tarafından kullanılamaz
2. Araba nesnesine ait motor nesnesinin ömrü hemen hemen araba ile başlayıp, araba ile son bulur.
Burada örneğin ikinci madde bazı durumlarda gerçekleşmeyebilir. Ama bu ilişkinin genel durumunu bozmaz.

Bir kodun derlenebilmesi için başka bir bildirimin var ve erişilebilir olması gerektiği duruma "bağımlılık (dependency)" denir.

İki sınıf arasında genel olarak aşağıdaki ilişkilerden ya hiçbirisi yoktur ya da bir tanesi vardır:

1. ***İçerme (Composition) (has a):*** A ve B arasındaki "A has a B" ilişkisi için aşağıdaki iki koşulun da gerçeklenmesi gerekir, ya da tersine aşağıdaki iki koşulun da gerçeklendiği ilişkidir:
	- A nesnesine ait B nesnesi başka bir nesne tarafından kullanılmayacak
	- A nesnesine ait B nesnesi ömrüne hemen hemen A ile başlayacak ve hemen hemen A nesnesi ile ömrü son bulacak\
	Bu ilişkide A nesnesi kendisine ait B nesnesini istediği bir durumda (birçok durumda ya da hemen her durumda) kullanabilmektedir. Bu tarz kullanıma bütünsel (whole) kullanım denir.
2. ***Birleşme (Aggregation) (holds a):*** A ve B arasındaki "A holds a B" ilişkisi compostion ilişkisine ilişkin kuralların en az birisinin gerçeklenmediği bütünsel kullanım ilişkisidir.
3. ***Çağrışım (Association):*** A nesnesinin B nesnesini ihtiyacı olduğunda kullanması, saklamaması ilişkisidir. Yani bütünsel bir kullanım yoktur. Bu kullanıma "parçalı (partial) kullanım" da denir.
4. ***Türetme/Kalıtım (Inheritance) (is a):*** Biyoloji'den programlamaya aktarılmıştır. Biyoloji'de kalıtım ebeveynin (parent) özelliklerinin çocuğuna (child) aktarılmasıdır. Programlamaya ilişkin detayları ileride ele alınacaktır.

***Anahtar Notlar:*** Yukarıdaki ilişkilerden "inheritance" dışında kalan ilişkiler için Kotlin'de doğrudan sentaks ve semantik kurallar yoktur. Dilin genel sentaks ve semantic kuralları ile ilişkinin kuralları doğrultusunda gerçekleştirilebilir (implementation). Ancak "inheritnace" için Kotlin'de ayrı sentaks ve semantik kurallar ayrıdır.

***Anahtar Notlar:*** Bazı sınıfların implementasyonları gereği yukarıdaki ilişkilerden hiçbirisi olmayabilir. Ya da bazı özel durumlar dolayısıyla da yukarıdaki ilişkilerden biri olmayabilir. Ama ortada yine bir bağımlılık (dependency) söz konusudur. Bu da yine genel durumu bozmaz. Böylesi durumlar ileride ele alıncaktır

>*A ile B arasındaki composition (has a) ilişkisi*

```kotlin
package org.csystem.app

fun main()
{
    val a = A(/*....*/)

    a.doWork1()
    a.doWork2()

    //...
}

class A {
    private var mB: B

    init {
        mB = B(/*...*/)
    }

    fun doWork1()
    {
        mB.doSomething()
        //...
    }

    fun doWork2()
    {
        mB.doSomething()
        //...
    }

    //...
}

class B {
    //...
    fun doSomething()
    {
        //...
    }
    //...
}
```

>*A ile B arasındaki aggregation (holds a) ilişkisi*

```kotlin
package org.csystem.app

fun main()
{
    val b1 = B(/*...*/)
    val a = A(b1/*...*/)

    a.doWork1()
    a.doWork2()

    val b2 = B(/*...*/)

    a.b = b2

    a.doWork1()
    a.doWork2()
}

class A(var b: B/*...*/) {
    //...
    fun doWork1()
    {
        b.doSomething()
        //...
    }

    fun doWork2()
    {
        b.doSomething()
        //...
    }

    //...
}

class B {
    //...
    fun doSomething()
    {
        //...
    }
    //...
}
```

>*Car, Engine, Plane, Driver ve Pilot sınıfları arasındaki ilişkiler*

***Anahtar Notlar:*** Kotlin'de referans dizileri Array generic sınıfının bir açılımı olarak bildirilir. Bu konu ileride ele alınacaktır. Örnekte yalnızca sınıflararası ilişkilere odaklanınız

```kotlin
package org.csystem.app

fun main()
{
    val driver = Driver("Eray Taşay")
    val car = Car(driver/*...*/)
    val pilots = arrayOf(Pilot("Anıl Topuz", "1. Pilot"), Pilot("Bora Şahin", "2. Pilot"), Pilot("Emirhan Kabal", "3.Pilot"))
    val plane = Plane(4, pilots/*...*/)

    car.run()
    println("----------------------------------------------")
    plane.fly()
}

class Plane(engineCount: Int /*...*/, var pilots: Array<Pilot>) {
    private var mEngines: Array<Engine>
    init {
        mEngines = Array(engineCount) {Engine(/*...*/)}
        //...
    }

    //...

    private fun startEngines()
    {
        for (engine in mEngines)
            engine.startEngine()
    }

    private fun accelerateEngines()
    {
        for (engine in mEngines)
            engine.accelerateEngine()
    }

    private fun slowEngines()
    {
        for (engine in mEngines)
            engine.slowEngine()
    }

    private fun stopEngines()
    {
        for (engine in mEngines)
            engine.stopEngine()
    }


    fun fly()
    {
        println("Pilots")
        for (p in pilots)
            println("${p.title}, ${p.name}")

        startEngines()
        accelerateEngines()

        println("flying!...")
        //...

        slowEngines()
        stopEngines()
    }

    //...
}

class Pilot(var name: String, var title: String) {
    //...
}

class Car(var driver: Driver) {
    private var mEngine: Engine = Engine(/*....*/)

    //...

    fun brake()
    {
        println("brake")
        mEngine.slowEngine()
    }

    fun run()
    {
        println("Driver:${driver.name}")
        mEngine.startEngine()
        mEngine.accelerateEngine()

        println("running!...")

        //...

        brake()
        mEngine.stopEngine()
    }

    //...
}

class Driver(var name: String/*...*/) {
    //...
}

class Engine {
    //...
    fun startEngine()
    {
        println("start engine")
    }

    fun accelerateEngine()
    {
        println("accelerate engine")
    }

    fun slowEngine()
    {
        println("slow engine")
    }

    fun stopEngine()
    {
        println("stop engine")
    }
    //...
}
```

>*A ile B arasındaki association ilişkisi*

```kotlin
package org.csystem.app

fun main()
{
    val b = B(/*...*/)
    val a = A(/*...*/)

    a.doWork(b)

    val b1 = B(/*...*/)

    a.doWork(b1)
}

class A {
    //...
    fun doWork(b: B)
    {
        //...
        b.doSomething()
        //...
    }
    //
}


class B {
    fun doSomething()
    {
        //...
    }
}
```

>*Taxi, Driver ve Client arasındaki ilişkiler*

```kotlin
package org.csystem.app

fun main()
{
    val driver = Driver(/*...*/)
    val taxi = Taxi(driver/*...*/)

    //...

    val client = Client(/*...*/)

    taxi.take(client)

    val client2 = Client(/*...*/)

    taxi.take(client2)

    //...
}

class Taxi(var driver: Driver /*...*/) {
    //...

    fun take(c: Client)
    {
        //...
    }
}

class Client {
    //...
}

class Driver {
    //...
}
```

>*Aşağıdaki örnekte Person sınıfı ile String sınıfı arasındaki ilişki ne tam bir composition ne de tam bir aggregation ilişkisidir. Buradaki durum istisnadır ve ilişki anlamında isimlendirilmesi gerekmez*

```kotlin
package org.csystem.app

fun main()
{
    var name = "Ali"
    val person = Person(name, 1)

    name = "ali"
    println("${person.name}, ${person.no}")
}

class Person(var name: String, var no: Int)
```

>*Türetme/Kalıtım (Inheritance) kavramı programlamada bir sınıfı kodlarına dokunmadan ve/veya kodlarını kopyalamadan genişletmek (extension) için kullanılır. Burada genişletme var olan özelliklere ekleme olarak düşünülebilir. Bu ilişkide B sınıfı A sınıfından türetilmişse "B is an A" cümlesi geçerli olur.*

>*B sınıfı A sınıfından türetilmiş olsun. B sınıfına A sınıfının bir türemiş sınıfı (derived class) denir. A sınıfına da B sınıfının taban sınıfı (base class) denir. Bu iki terim nesne yönelimli programlama tekniğine ilişkin genel terimlerdir. Kotlin'de ve Java'da "base class" yerine "super class", "derived class" yerine de "sub class" terimleri daha çok kullanılır.*

***Anahtar Notlar:*** Nesne yönelimli programlamda "base class" yerine Biyoloji'den gelen terim olan "parent class", "derived class" yerine de yine Biyoloji'den gelen "child class" terimleri de kullanılır.

>*Bir dizi türetme söz konusu olabilir. Örneğin C sınıfı B sınıfından, B sınıfı da A sınıfından türetilmiş olsun. Bu durumda C'nin taban sınıfı (super class) dendiğinde doğrudan taban sınıf (direct super class) olan B sınıfı anlaşılır. Bu hiyerarşide A sınıfı C'nin dolaylı taban sınıfıdır (indirect super class.) Örneğimizde "C nin taban sınıfları B ve A'dır" cümlesi teknik olarak doğru değildir. Doğrusu "C'nin taban sınıfı B'dir, dolaylı taban sınıflarından biri A'dır" cümlesidir.*

>*Kotlin'de ve Java'da bir sınıf birden fazla (doğrudan) taban sınıfa sahip olamaz. Yani çoklu türetme (multiple inheritance) yoktur. Bir sınıf yalnızca tek bir sınıftan türetilebilir.*

***Anahtar Notlar:*** Kotlin'de ve Java'da çoklu türetmenin gerektiği yerlerde kısmi (partial) olarak desteklenmesini sağlayan "interface" denilen bir tür bulunmaktadır. Çoklu türetmenin pratikteki gerekliliği "interface"'ler ile karşılanabilmektedir. "interface" konusu ileride ele alınacaktır.

>*Türetme kavramı programlamada "bir sınıfın kodlarına dokunmadan o sınıfı genişletmek" anlamına gelir. Bu da OCP'nin "open for extension closed for modification" mottosuna uygun olduğunu gösterir*

>*Anımsanacağı gibi Java'da bir sınıf final anahtar sözcüğü ile bildirilmemişse türetmeye açıktır. Yani bu durumda Java'da bir sınıf default olarak türetmeye açıktır denebilir. Ancak Kotlin'de bir sınıf default olarak türetmeya açık değildir. Bir sınıfın türtilebilir olması yani türetmeye açık olması için open anahtar sözcüğü ile bildirilmesi gerekir*

>*Türetme işlemi : atomu ile yapılır*

```kotlin
package org.csystem.app

class B : A() {
    //...
}

open class A {
    //...
}
```

>*Anımsanacağı gibi türemiş sınıf nesnesi içerisinde taban sınıf nesnesi kadarlık bir bellek bölgesi de bulunmaktadır. Bu anlamda türemiş sınıf nesnesi taban sınıf nesnesini kapsar durumdadır. Bu kapsama mantıksal değildir. Yani bellek kapsamadır*

```kotlin
package org.csystem.app

fun main()
{
    val b = B()

    b.x = 10
    b.y = 20

    println("x = ${b.x}, y = ${b.y}")
}

class B : A() {
    var y: Int = 0
    //...
}

open class A {
    var x: Int = 0
    //...
}
```

>*Anımsanacağı gibi Java'da (dolayısıyla Kotlin JVM'de) bir nesnenin yaratılması adımları şunlardır:*\
    *1. Bellekte yer ayrılır*\
    *2. non-static olan ve final olmayan veri elemanlarına default değerler atanır.*\
    *3. ctor çağrılır*

>*Bir nesnenin yartılmasının tamamlanması yani adresinin elde edilmesi bu 3 adımın düzgün bir biçimde tamamlanmasıyla olur. Bu adımlar herhangi birinde problem olursa nesne yaratılmamış olur. Örneğin ctor'da bir exception oluştuğunda nesne yaratılması adımları düzgün bir biçimde tamamlanmadığı için nesne yaratılmış olmaz*

>*Türemiş sınıf içerisinde taban sınıfın ctor'unun çağrılması durumu. Aşağıdaki örnekte taban sınıfın default ctor'u kullanılarak türetme yapıldığından B'nin mutlaka primary olarak default ctor'u olmalıdır*

```kotlin
package org.csystem.app

fun main()
{
    val x = B()
    println("--------------------------------------------")
    val y = B(10)
    //...
}

class B() : A() {
    init {
        println("B.constructor()")
    }

    constructor(a: Int) : this()
    {
        println("B.constructor(Int)")
    }
}

open class A {
    constructor()
    {
        println("A.constructor()")
    }

}
```

>*Taban sınıfın herhangi bir ctor'unun çağrılmasının sağlanması*

```kotlin
package org.csystem.app

fun main()
{
    var x = B(10)
    println("-------------------------------------")
    var y = B(1.0)
    println("-------------------------------------")
    var z = C()

    //...
}


class C : A() {
    init {
        println("default ctor of C")
    }
}

class B(x: Int) : A(x) {
    init {
        println("ctor(Int) of B")
    }

    constructor(x: Double) : this(x.toInt())
    {
        println("ctor(Double) of B")
    }
}

open class A(c: Int) {
    init {
        println("ctor(Int) of A")
    }

    constructor() : this(10)
    {
        println("default ctor of A")
    }
}
```

>*Taban sınıfın herhangi bir ctor'unun super anahtar sözcüğü kullanılarak çağrılmasının sağlanması. Taban sınıfın primary ctor'u yoksa türetmede :'den sonra doğrudan sınıf ismi kullanılır*

```kotlin
package org.csystem.app

fun main()
{
    B()
    println("-------------------------------------")
    B(10)
}

class B : A {
    constructor() //: super()
    {
        println("default ctor of B")
    }

    constructor(x: Int) : super(x)
    {
        println("ctor(Int) of B")
    }
}

open class A {
    constructor(x: Int)
    {
        println("ctor(Int) of A")
    }

    constructor()
    {
        println("default ctor of A")
    }
}
```

>*AnalyticalCircle sınıfı*

```kotlin
package org.csystem.app

import org.csystem.math.geometry.AnalyticalCircle

fun main()
{
    val ac = AnalyticalCircle()

    println("Radius:${ac.radius}")
    println("Area:${ac.area}")
    println("Circumference:${ac.circumference}")
    println("Center:${ac.x}, ${ac.y}")

    ac.radius = -2.3
    ac.x = 200.0
    ac.y = 12.3

    println("Radius:${ac.radius}")
    println("Area:${ac.area}")
    println("Circumference:${ac.circumference}")
    println("Center:${ac.x}, ${ac.y}")
}
```

```kotlin
package org.csystem.app

import org.csystem.math.geometry.AnalyticalCircle

fun main()
{
    val ac = AnalyticalCircle(-2.3, 23.5, 56.7)

    println("Radius:${ac.radius}")
    println("Area:${ac.area}")
    println("Circumference:${ac.circumference}")
    println("Center:${ac.x}, ${ac.y}")

    ac.radius = 2.3
    ac.x = 200.0
    ac.y = 12.3

    println("Radius:${ac.radius}")
    println("Area:${ac.area}")
    println("Circumference:${ac.circumference}")
    println("Center:${ac.x}, ${ac.y}")
}
```

```kotlin
package org.csystem.app

import org.csystem.math.geometry.AnalyticalCircle

fun main()
{
    val ac = AnalyticalCircle(x = 23.5, y = 56.7)

    println("Radius:${ac.radius}")
    println("Area:${ac.area}")
    println("Circumference:${ac.circumference}")
    println("Center:${ac.x}, ${ac.y}")

    ac.radius = 2.3
    ac.x = 200.0
    ac.y = 12.3

    println("Radius:${ac.radius}")
    println("Area:${ac.area}")
    println("Circumference:${ac.circumference}")
    println("Center:${ac.x}, ${ac.y}")
}
```

```kotlin
package org.csystem.app

import org.csystem.math.geometry.AnalyticalCircle

fun main()
{
    val ac1 = AnalyticalCircle(3.0, 100.0, 200.0)
    val ac2 = AnalyticalCircle(2.0, 97.0, 204.0)
    val centerDistance = ac1.centerDistance(ac2)

    println("Center distance:$centerDistance")
    println(if (ac1.isTangent(ac2)) "Teğet" else "Teğet değil")
}
```

```kotlin
/*----------------------------------------------------------------------
	FILE        : AnalyticalCircle.kt
	AUTHOR      : Android-Mar-2023 Group
	LAST UPDATE : 03.05.2023

	AnalyticalCircle class that represents a circle in cartesian plane

	Copyleft (c) 1993 by C and System Programmers Association
	All Rights Free
-----------------------------------------------------------------------*/
package org.csystem.math.geometry

import kotlin.math.abs

open class AnalyticalCircle(radius: Double = 0.0, x: Double = 0.0, y: Double = 0.0) : Circle (radius) {
    private val mCenter = MutablePoint(x, y)
    constructor(radius: Double = 0.0, center: MutablePoint) : this(radius, center.x, center.y)
    constructor(radius: Double = 0.0, center: Point) : this(radius, center.x, center.y)

    var x: Double
        get() = mCenter.x
        set(value)
        {
            mCenter.x = value
        }

    var y: Double
        get() = mCenter.y
        set(value)
        {
            mCenter.y = value
        }

    fun centerDistance(other: AnalyticalCircle) = mCenter.distance(other.mCenter)
    fun isTangent(other: AnalyticalCircle) = abs(centerDistance(other) - radius - other.radius) < 0.00001
    //...
}
```

```kotlin
/*----------------------------------------------------------------------
	FILE        : Circle.kt
	AUTHOR      : Android-Mar-2023 Group
	LAST UPDATE : 03.05.2023

	Circle class that represents the circle in geometry

	Copyleft (c) 1993 by C and System Programmers Association
	All Rights Free
-----------------------------------------------------------------------*/
package org.csystem.math.geometry

import kotlin.math.abs

open class Circle(radius: Double = 0.0) {
    var radius: Double = abs(radius)
        set(value) {
            field = abs(value)
        }

    val area : Double
        get() = Math.PI * radius * radius

    val circumference : Double
        get() = 2 * Math.PI * radius
}
```

```kotlin
/*----------------------------------------------------------------------
	FILE        : MutablePoint.kt
	AUTHOR      : Android-Mar-2023 Group
	LAST UPDATE : 03.05.2023

	Mutable Point class that represents a point in cartesian plane

	Copyleft (c) 1993 by C and System Programmers Association
	All Rights Free
-----------------------------------------------------------------------*/
package org.csystem.math.geometry

import kotlin.math.sqrt

class MutablePoint(var x: Double = 0.0, var y: Double = 0.0) {
    constructor(x: Int, y: Int) : this(x.toDouble(), y.toDouble()) //optional
    fun distance(a: Double = 0.0, b: Double = 0.0) = sqrt((x - a) * (x - a) + (y - b) * (y - b))
    fun distance(other: MutablePoint) = distance(other.x, other.y)

    fun offset(dx: Double, dy: Double = dx)
    {
        x += dx
        y += dy
    }
}
```

```kotlin
/*----------------------------------------------------------------------
	FILE        : Point.kt
	AUTHOR      : Android-Mar-2023 Group
	LAST UPDATE : 03.05.2023

	Immutable Point class that represents a point in cartesian plane

	Copyleft (c) 1993 by C and System Programmers Association
	All Rights Free
-----------------------------------------------------------------------*/
package org.csystem.math.geometry

import kotlin.math.sqrt

class Point(val x: Double = 0.0, val y: Double = 0.0) {
    constructor(x: Int, y: Int) : this(x.toDouble(), y.toDouble()) //optional
    fun distance(a: Double = 0.0, b: Double = 0.0) = sqrt((x - a) * (x - a) + (y - b) * (y - b))
    fun distance(other: Point) = distance(other.x, other.y)
}
```

>*Aralarında türetme ilişkisi olmayan iki sınıf türünden referans birbirine doğrudan atanamaz*

```kotlin
package org.csystem.app

fun main()
{
    val a = A()
    val b: B

    b = a //error

}

class A {
    //...
}

class B {
    //...
}
```

>*Aralarında türetme ilişkisi olmayan sınıflar türünden iki referans as operatörü ile birbirine doğrudan atanabilir. \
>Aşağıdaki kodda exception oluşur. as operatörü ileride detaylı olarak incelenecektir*

```kotlin
package org.csystem.app

fun main()
{
    val a = A()
    var b: B = a as B

    //...
}

class A {
    //...
}

class B {
    //...
}
```

>*Türemiş sınıf (sub class) türünden  bir referans taban sınıf (super class) türünden bir referansa doğrudan (implicit) atanabilir (upcasting)*

```kotlin
package org.csystem.app

fun main()
{
    val b = B(10, 20)

    println("b.x = ${b.x}, b.y = ${b.y}")
    val a: A = b

    println("a.x = ${a.x}")
}


class B(var y: Int, x: Int) : A(x)

open class A(var x: Int)

```

>*Türemiş sınıf (sub class) türünden  bir referans taban sınıf (super class) türünden bir referansa doğrudan (implicit) atanabilir (upcasting). Bu durumda türemiş sınıf nesnenin taban sınıf kısmının adresi taban sınıf referansına atanmış olur*

```kotlin
package org.csystem.app

fun main()
{
    val b = B(10, 20)

    println("b.x = ${b.x}, b.y = ${b.y}")
    val a: A = b

    println("a.x = ${a.x}")

    ++a.x
    println("a.x = ${a.x}")
    println("b.x = ${b.x}, b.y = ${b.y}")
}

class B(var y: Int, x: Int) : A(x)

open class A(var x: Int)
```

>*upcasting'in anlamı*

```kotlin
package org.csystem.app

fun main()
{
    val a = A(10)
    val b = B(10, 20)
    val c = C(10, 20, 30)

    doWork(a)
    doWork(b)
    doWork(c)
}

fun doWork(a: A)
{
    println("a.x = ${a.x}")
}

open class A(var x: Int)

open class B(var y: Int, x: Int) : A(x)

class C(var z: Int, x: Int, y: Int) : B(y, x)
```

>*CompanyApp uygulaması. Örnekte payInsurance fonksiyonun türden bağımsız yazıldığına dikkat ediniz*

```kotlin
package org.csystem.app.company

private fun getWorker() = Worker("Ali", "12345678912", "Geyikli", 400.0, 7)
private fun getManager() = Manager("Veli", "12345678914", "Silivri", "Pazarlama", 50000.0)
private fun getProjectWorker() = ProjectWorker("Selami", "12345678918", "Şişli", 400.0, 7, 13000.89)

fun main() = runApplication()

fun runApplication()
{
    val hr = HumanResources()
    val worker = getWorker()
    val manager = getManager()
    val projectWorker = getProjectWorker()

    hr.payInsurance(worker)
    hr.payInsurance(manager)
    hr.payInsurance(projectWorker)
}

package org.csystem.app.company

open class Employee(var name: String = "", var citizenId: String = "", var address: String = ""/*...*/) {
    //...
}

package org.csystem.app.company

class HumanResources {
    //...
    fun payInsurance(employee: Employee)
    {
        println("Name:${employee.name}, CitizenId:${employee.citizenId}, Address:${employee.address}")
        //...
    }
}

package org.csystem.app.company

open class Manager(name: String = "", citizenId: String = "", address: String = "",
                   var department: String = "", var salary: Double = 0.0) : Employee(name, citizenId, address) {
    //...
}

package org.csystem.app.company

open class ProjectWorker(name: String = "", citizenId: String = "", address: String = "",
                         feePerHour: Double = 0.0, hourPerDay: Int = 0, val extraFee: Double = 0.0) : Worker(name, citizenId, address, feePerHour, hourPerDay) {
    //...
}

package org.csystem.app.company

open class Worker(name: String = "", citizenId: String = "", address: String = "",
             var feePerHour: Double = 0.0, var hourPerDay: Int = 0) : Employee(name, citizenId, address) {
    //...
}
```

>*Bir referansın iki tane türü vardır: static type, dynmaic type* 
Referansın static türü bildirildiği türdür. Derleme zamanına (compile time) ilişkindir ve değişmez. Referansın türü dendiğinde static tür anlaşılır.

>*Referansın dinamik türü ise referansın gösterdiği nesnenin bellekte yaratıldığı gerçek türüdür*

***Anahtar Notlar:*** Java'da bir referansın dinamik tür bilgisi "fully qualified" olarak şu şekilde elde edilebilir: `<referans>.getClass().getName()`
Bunun Kotlin karşılığı: `<referans>.javaClass.name` biçimindedir 

>*Bir referansın dinamik türü o referansın çalışma zamanı sırasında bellekte gösterdiği gerçek nesnenin türüdür*

```kotlin
package org.csystem.app

import org.csystem.util.console.kotlin.readInt

fun main()
{
    while (true) {
        val value = readInt("Bir sayı giriniz:")

        if (value == 0)
            break

        val x: A

        x = if (value > 0) B() else A()

        println(x.javaClass.name) // x referansının dinamik türü stdout'a yazdırılıyor
    }
    println("Tekrar yapıyor musunuz?")
}

open class A {
    //...
}

class B : A() {
    //...
}
```

>*Bir referansın dinamik türü o referansın çalışma zamanı sırasında bellekte gösterdiği gerçek nesnenin türüdür*

```kotlin
package org.csystem.app

import org.csystem.util.console.kotlin.readInt
import kotlin.random.Random

fun main()
{
    val count = readInt("Bir sayı giriniz:")

    for (i in 1..count) {
        val x: A = createRandomA()

        println(x.javaClass.name)
    }

    println("Tekrar yapıyor musunuz?")
}

fun createRandomA(random: Random = Random) : A
{
    return when (random.nextInt(5)) {
        0 -> B()
        1 -> C()
        2 -> D()
        3 -> E()
        else -> A()
    }
}

open class A {
    //...
}

open class B : A() {
    //...
}

open class C : B() {
    //...
}

class D : A() {
    //...
}

class E : C() {
    //...
}
```

>*Taban sınıf türünden bir referans türemiş sınıf türünden bir referansa as operatörü ile atanabilir (downcasting). Bu işlem derleme zamanından geçmek içindir. Bu durumda çalışma zamanında kaynak referansın dinamik türünün as operatörüne verilen türü kapsayıp kapsamadığına bakılır. Kapsıyorsa haklı dönüşümdür, akış devam eder. Kapsamıyorsa haksız dönüşümdür, exception oluşur*

```kotlin
package org.csystem.app

import org.csystem.util.console.kotlin.readInt
import kotlin.random.Random

fun main()
{
    val count = readInt("Bir sayı giriniz:")

    for (i in 1..count) {
        val x: A = createRandomA()

        println(x.javaClass.name)

        val y: C = x as C

        y.c = 10

        println("y.c = ${y.c}")
    }

    println("Tekrar yapıyor musunuz?")
}

fun createRandomA(random: Random = Random) : A
{
    return when (random.nextInt(5)) {
        0 -> B()
        1 -> C()
        2 -> D()
        3 -> E()
        else -> A()
    }
}

open class A {
    //...
}

open class B : A() {
    //...
}

open class C(var c: Int  = 10) : B() {
    //...
}

class D : A() {
    //...
}

class E : C() {
    //...
}
```

>*Any sınıfı tüm sınıfların doğrudan ya da dolaylı olarak taban sınıfı biçimindedir. Java ve C# daki Object sınıfının Kotlin'deki karşılığıdır. Bu durumda Kotlin'de tüm referanslar Any türden bir referansa doğrudan (implicit) olarak atanabilir*

```kotlin
package org.csystem.app

fun main()
{
    val a: Any = Sample()
    var s: Sample = a as Sample

    //...
}

class Sample  {
    //...
}
```

>*Temel türler de bir sınıf olarak temsil edildiğinden Kotlin anlamında zaten kutulama (boxing) yapılmış olur. Ancak aşağı seviyede kutulama (Java anlamında kutulama da denebilir) Any sınıfına atamada gerçekleşir*

```kotlin
package org.csystem.app

fun main()
{
    val a = 20
    val any: Any = a //boxing

    println(a.javaClass.name)
    println(any.javaClass.name)

    val value = any as Int //unboxing

    println(value)
}
```

>*Anımsanacağı gibi bir referansın dinamik türünün bir türü kapsayıp kapsamadığı instanceof operatörü ile test edilebilir. Bu operatör "downcasting" işleminin güvenli bir biçimde yapılabilmesi için kullanılır. Kotlin'de bu işlem `is` ve `!is` operatörü ile yapılabilir. `is` operatörü instance of operatörüne karşılık gelir. `!is` operatörü de Java'da aşağıdaki bir kontrolün karşılığı olarak düşünülebilir:*

```
if (!(a instanceof T))
            ...
     Kotlin'deki karşılığı:
        if (a !is T)
            ...
```

>*Bu operatörün kullanımına ilişkin bazı  detaylar ileride ele alınacaktır*

>*is operatörü*

```kotlin
package org.csystem.app

import org.csystem.util.console.kotlin.readInt
import kotlin.random.Random

fun main()
{
    val count = readInt("Bir sayı giriniz:")

    for (i in 1..count) {
        val x: A = createRandomA()

        println("---------------------------------------")
        println(x.javaClass.name)

        if (x is C) {
            val y: C = x as C
            y.c = 10

            println("y.c = ${y.c}")
        }
        else
            println("Haksız dönüşüm")

        println("---------------------------------------")
    }

    println("Tekrar yapıyor musunuz?")
}

fun createRandomA(random: Random = Random) : A
{
    return when (random.nextInt(5)) {
        0 -> B()
        1 -> C()
        2 -> D()
        3 -> E()
        else -> A()
    }
}

open class A {
    //...
}

open class B : A() {
    //...
}

open class C(var c: Int  = 10) : B() {
    //...
}

class D : A() {
    //...
}

class E : C() {
    //...
}
```

>*!is operatörü*

```kotlin
package org.csystem.app

import org.csystem.util.console.kotlin.readInt
import kotlin.random.Random

fun main()
{
    val count = readInt("Bir sayı giriniz:")

    for (i in 1..count) {
        val x: A = createRandomA()

        println("---------------------------------------")
        println(x.javaClass.name)

        if (x !is C)
            println("Haksız dönüşüm")
        else {
            val y: C = x as C
            y.c = 10

            println("y.c = ${y.c}")
        }


        println("---------------------------------------")
    }

    println("Tekrar yapıyor musunuz?")
}

fun createRandomA(random: Random = Random) : A
{
    return when (random.nextInt(5)) {
        0 -> B()
        1 -> C()
        2 -> D()
        3 -> E()
        else -> A()
    }
}

open class A {
    //...
}

open class B : A() {
    //...
}

open class C(var c: Int  = 10) : B() {
    //...
}

class D : A() {
    //...
}

class E : C() {
    //...
}
```

>*Kotlin'de akıllı dönüşüm (smart cast) denilen bir kavram vardır. Derleyici downcasting ya da unboxing durumlarında hedef türe dönüştürmenin güvenli olduğunu anlarsa as operatörüne gerek kalmaksınız dönüşüme ilişkin kod derleme zamanında otomatik olarak yazılır. Akıllı dönüşüm diğer detaylarıyla birlikte ileride ele alınacaktır*

```kotlin
package org.csystem.app

import org.csystem.util.console.kotlin.readInt
import kotlin.random.Random

fun main()
{
    val count = readInt("Bir sayı giriniz:")

    for (i in 1..count) {
        val x: A = createRandomA()

        println("---------------------------------------")
        println(x.javaClass.name)

        if (x is C) {
            x.c = 10 //smart cast
            println("x.c = ${x.c}")
        }
        else
            println("Haksız dönüşüm")

        println("---------------------------------------")
    }

    println("Tekrar yapıyor musunuz?")
}

fun createRandomA(random: Random = Random) : A
{
    return when (random.nextInt(5)) {
        0 -> B()
        1 -> C()
        2 -> D()
        3 -> E()
        else -> A()
    }
}

open class A {
    //...
}

open class B : A() {
    //...
}

open class C(var c: Int  = 10) : B() {
    //...
}

class D : A() {
    //...
}

class E : C() {
    //...
}

```

>*Any türünden bir dizide heterojen türler tutulabilir. is operatörü ile de dinamik tür kontrolü yapılabilir*

***Anahtar Notlar:*** Kotlin'de referans dizileri Array isimli generic bir sınıf ile yaratılabilir. Bu konu ileride ele alınacaktır.

>*Aşağıdaki örneği inceleyiniz*

```kotlin
package org.csystem.app.generator.random

import org.csystem.generator.random.createRandomAnyArray
import org.csystem.math.geometry.Circle
import org.csystem.math.geometry.Point
import org.csystem.util.console.kotlin.readInt

fun main() = runApplication()

fun runApplication()
{
    val count = readInt("Bir sayı giriniz:")
    val any = createRandomAnyArray(count)

    for (a in any) {
        println("----------------------------------")
        println(a.javaClass.name)

        if (a is String)
            println("$a -> ${a.uppercase()}")
        else if (a is Int)
            println("$a * $a = ${a * a}")
        else if (a is Point)
            println("(${a.x}, ${a.y})")
        else if (a is Boolean)
            println("flag = $a, !flag = ${!a}")
        else if (a is Circle)
            println("Radius:${a.radius}, Area:${a.area}, Perimeter:${a.circumference}")

        println("----------------------------------")
    }
}

package org.csystem.app.generator.random

import org.csystem.generator.random.createRandomAnyArray
import org.csystem.math.geometry.Circle
import org.csystem.math.geometry.Point
import org.csystem.util.console.kotlin.readInt

fun main() = runApplication()

fun runApplication()
{
    val count = readInt("Bir sayı giriniz:")
    val any = createRandomAnyArray(count)

    for (a in any) {
        println("----------------------------------")
        println(a.javaClass.name)

        when (a) {
            is String -> println("$a -> ${a.uppercase()}")
            is Int -> println("$a * $a = ${a * a}")
            is Point -> println("(${a.x}, ${a.y})")
            is Boolean -> println("flag = $a, !flag = ${!a}")
            is Circle -> println("Radius:${a.radius}, Area:${a.area}, Perimeter:${a.circumference}")
        }

        println("----------------------------------")
    }
}

package org.csystem.generator.random

import org.csystem.math.geometry.Circle
import org.csystem.math.geometry.Point
import kotlin.random.Random

private fun createRandomString(count: Int, random: Random) : String
{
    val sb = StringBuilder()

    for (i in 1..count)
        sb.append((if (random.nextBoolean()) 'A' else 'a') + random.nextInt(26))

    return sb.toString()
}

//String, Int, Point, Boolean, Circle
private fun createRandomAny(random: Random) : Any
{
    return when (random.nextInt(5)) {
        0 -> createRandomString(random.nextInt(5, 15), random)
        1 -> random.nextInt(-128, 128)
        2 -> Point(random.nextDouble(-100.0, 100.0), random.nextDouble(-100.0, 100.0))
        3 -> random.nextBoolean()
        else -> Circle(random.nextDouble(-3.0, 3.0))
    }
}

fun createRandomAnyArray(count: Int, random: Random = Random) : Array<Any>
{
    val result = Array<Any>(count){}

    for (i in result.indices)
        result[i] = createRandomAny(random)

    return result
}
```

### Polymorphism (çok biçimlilik):

>*Biyolojiden programlamaya aktarılmıştır. Biyoloji'de "polymorphism", farklı doku ya da organların evrim süreci içerisinde temel hedefleri aynı kalması koşuluyla o hedefe nasıl ulaşılacağının değişebilmesidir.*

>*Aslında polymorphism ikiye ayrılır: run time polymorphism, compile time polymorphism. Bu bölümde anlatılan ve Biyoloji'den gelen "runtime polymophism"'dir. Polymorphism dendiğinde "runtime polymorphism" anlaşılır. Compile time polymorphism Kotlin'de "generic"'ler ile gerçekleştirilir. Bu konu ileride ele alınacaktır*

>*Çok biçimliliğin programlama açısından 3(üç) tane tanımından bahsedilebilir:*
>1. *Biyolojik Tanım: Taban sınıfın bir fonksiyonunun türemiş sınıfta yeniden gerçekleştirilmesi (implementation)
>2. *Yazılım Mühendisliği Tanımı: Türden bağımsız kod yazmak*
>3. *Aşağı Seviyeli Tanım: Önceden yazılmış kodların sonradan yazılmış kodları çağırabilmesi*

>*Çok biçimlilik sanal metotlar (virtual method) kullanılarak gerçekleştirilir. Kotlin'de bir metodun sanal olması için "open" anahtar sözcüğü ile bildirilmesi gerekir. Ayrıca türemiş sınıfta override edilen metot için de override anahtar sözcüğü kullanılmalıdır. open ile bildirilmiş sanal bir metodun türemiş sınıfta override edilmesi zorunlu değildir*

***Anahtar Notlar:*** Java'da non-static olan, final olmayan veya abstract olarak bildirilmiş bir metot sanaldır. Kotlin'de ise bir metodun sanal olması programcı tarafından bildirilmelidir. private metotlar sanal olamazlar

***Anahtar Notlar:*** Global fonksiyonlar sanal olamazlar

>*Derleyici sanal bir metot çağrısı gördüğünde şu şekilde bir kod üretir: Derleme zamanında referansın dinamik türüne bak, dinamik türe ilişkin sınıfta sanal metot override edilmişse onu çağır. Edilmemişse sırasıyla taban sınıfına ve dolaylı sınıflarına da bak ilk bulduğun metodu çağır*

>*Aşağıdaki örneği çalıştırıp sonucu gözlemleyiniz*

```kotlin
package org.csystem.app

import org.csystem.util.console.kotlin.readInt
import kotlin.random.Random

fun main()
{
    val n = readInt("Input count:")

    for (i in 1..n) {
        val x: A = createRandomA()

        println("-----------------------------------------------------------")
        println(x.javaClass.name)
        x.foo()
        println("-----------------------------------------------------------")
    }
}

fun createRandomA(random: Random = Random) : A
{
    return when(random.nextInt(4)) {
        0 -> A()
        1 -> B()
        2 -> C()
        else -> D()
    }
}

open class D : C() {
    //...
}

open class C : B() {
    override fun foo() //override
    {
        println("C.foo")
    }
}

open class B : A() {
    override fun foo() //override
    {
        println("B.foo")
    }
}

open class A {
    open fun foo() //sanal metot (virtual method)
    {
        println("A.foo")
    }
}
```

>*final override metot artık override işlemine kapatılmıştır*

```kotlin
package org.csystem.app

open class A {
    open fun foo() //sanal metot
    {
        println("A.foo")
    }
}

open class B : A() {
    final override fun foo()
    {
        println("A.foo")
    }
}

class C : B() {
    override fun foo() //error
    {
        println("C.foo")
    }
}
```

>*Aşağıdaki örnek Kotlin'de geçersizdir*

```kotlin
package org.csystem.app

open class A {
    open fun foo()
    {
        println("A.foo")
    }
    //...
}

open class B : A() {
    override fun foo()
    {
        println("B.foo")
    }
}

open class C : B() {
    open fun foo() //error
    {
        //...
    }
}
```

>*Aşağıdaki örnek Kotlin'de geçersizdir*

```kotlin
package org.csystem.app

open class A {
    open fun foo()
    {
        println("A.foo")
    }
    //...
}

open class B : A() {
    final override fun foo()
    {
        println("B.foo")
    }
}

open class C : B() {
    open fun foo() //error
    {
        //...
    }
}

```

***Anahtar Notlar:*** print ve println metotlarının `Any?` parametreli overload'ları toString metodunu çağırırlar ve elde edilen yazıyı stdout'a basarlar

>*Any sınıfının toString metodu nesneye yönelik tekil bir yazıya döner. Bu yazının nasıl elde edildiğinin programcı açısından önemi yoktur*

```kotlin
package org.csystem.app

fun main()
{
    val s1 = Sample()
    val s2 = Sample()

    println(s1)
    println(s2)
}

class Sample {

}
```

>*Aşağıdaki örnekte toString metodu override edilmiştir. Bu durumda println tarafından override edilen çağrılmış olur*

```kotlin
package org.csystem.app

fun main()
{
    val s1 = Sample()
    val s2 = Sample()

    println(s1)
    println(s2)
}

class Sample {
    override fun toString() = "Sample"
}

```

>*Complex sınıfının toString metodu*

```kotlin
package org.csystem.app

import org.csystem.kotlin.util.math.Complex

fun main()
{
    val z = Complex(2.3, -6.7)

    println(z)
}
```

>*Point sınıfının toString metodu*

```kotlin
package org.csystem.app

import org.csystem.math.geometry.Point

fun main()
{
    val z = Point(2.33456, -6.7789)

    println(z)
}
```

>*Any sınıfının equals metodu bir sınıf türünden nesnelerin eşitlik karşılaştırması için kullanılır. Bu durumda bu metot sınıfa özgü olarak override edilir. Bu metot aynı zamanda o sınıf referansların `==` ve `!=` operatörleri ile kullanıldıklarında da çağrılır.\
>
>Proje içerisindeki sınıfları ve equals metotlarını inceleyiniz*

```kotlin
package org.csystem.app

import org.csystem.math.geometry.Point

fun main()
{
    val p1 = Point(2.345, 5.678)
    val p2 = Point(2.345, 5.678)

    println(p1 == p2)
    println(p1 != p2)
}
```

***Anahtar Notlar:*** Temel türlere ilişkin sınıfların (Short, Int, Long, Byte, Float, Double, Boolean, Char) toString ve equals metotları da override edilmiştir. toString metotları ilgili değerin yazı karşılığına geri döner.

>*Bazı sınıflar bir kavramın soyut halini temsil ederler. Tek başlarına nesne olmalarının anlamı yoktur. Ondan türeyen sınıfların anlamı vardır. Bu tarz sınıflar abstract olarak bildirilirler. Bu anlamda bu sınıfların bazı metotlarının kodlarının da yani gövdesinin olması anlamsızdır. Bu durumda metot abstract olarak bildirilir. abstract metotların gövdeleri olmaz ve sanal metotlardır. Bu durumda abstract sınıf bir sınıf gören programcı o sınıfın abstract metotlarının olabileceğini ve somut (concrete) bir sınıf türetmek için o abstract metotları da override etmesi gerektiğini anlar. Ya da abstract bir sınıftan türetilmiş abstract olmayan (concrete) bir sınıf gördüğünde taban sınıfının tüm abstract metotlarını override ettiğini anlar*

>***abstract sınıflar ve metotlar**\
abstract sınıf türünden nesne programcı tarafından yaratılamaz. En az bir tane abstract metodu olan bir sınıf abstract olarak bildirilmelidir. abstract metotlar sanaldır*

```kotlin
package org.csystem.app

fun main()
{
    var a = A() //error
}

abstract class A {
    abstract fun foo()

}

class B : A() {
    override fun foo()
    {
        println("B.foo")
        super.foo() //error
    }
}
```

>*companyDemoApp uygulaması*

```kotlin
package org.csystem.app.company

private fun getWorker() = Worker("Ali", "12345678912", "Geyikli", 400.0, 7)
private fun getManager() = Manager("Veli", "12345678914", "Silivri", "Pazarlama", 50000.0)
private fun getSalesManager() = SalesManager("Fatma", "12345678916", "Mecidiyeköy", "Pazarlama", 50000.0, 10000.0)
private fun getProjectWorker() = ProjectWorker("Selami", "12345678918", "Şişli", 400.0, 7, 13000.89)

fun main() = runApplication()

fun runApplication()
{
    val hr = HumanResources()
    val worker = getWorker()
    val manager = getManager()
    val salesManager = getSalesManager()
    val projectWorker = getProjectWorker()

    hr.payInsurance(worker)
    hr.payInsurance(manager)
    hr.payInsurance(projectWorker)
    hr.payInsurance(salesManager)
}

package org.csystem.app.company

abstract class Employee(var name: String = "", var citizenId: String = "", var address: String = ""/*...*/) {
    abstract fun calculateInsurancePayment() : Double
    //...
}

package org.csystem.app.company

class HumanResources {
    //...
    fun payInsurance(employee: Employee)
    {
        println("Name:${employee.name}, CitizenId:${employee.citizenId}, Address:${employee.address}")
        println("Insurance Payment:${employee.calculateInsurancePayment()}")
    }
}

package org.csystem.app.company

open class Manager(name: String = "", citizenId: String = "", address: String = "",
                   var department: String = "", var salary: Double = 0.0) : Employee(name, citizenId, address) {
    override fun calculateInsurancePayment() = salary * 1.567
    //...
}

package org.csystem.app.company

open class ProjectWorker(name: String = "", citizenId: String = "", address: String = "",
                         feePerHour: Double = 0.0, hourPerDay: Int = 0, val extraFee: Double = 0.0) : Worker(name, citizenId, address, feePerHour, hourPerDay) {
    //...
    override fun calculateInsurancePayment() = super.calculateInsurancePayment() + extraFee
}

package org.csystem.app.company

open class SalesManager(name: String = "", citizenId: String = "", address: String = "",
                        department: String = "", salary: Double = 0.0, val extra: Double = 0.0) : Manager(name, citizenId, address, department, salary) {
    //...
    override fun calculateInsurancePayment() = super.calculateInsurancePayment() + extra
}

package org.csystem.app.company

open class Worker(name: String = "", citizenId: String = "", address: String = "",
             var feePerHour: Double = 0.0, var hourPerDay: Int = 0) : Employee(name, citizenId, address) {
    //...
    override fun calculateInsurancePayment() = feePerHour * hourPerDay *  30
}
```

>*abstract sınıfların property elemanı olabilir, ctor elemanı olabilir*

```kotlin
package org.csystem.app

fun main()
{
    val a: A = B("ankara", 6) //upcasting

    a.foo()
}

class B(str: String, x: Int, var y: Int = 0) : A(str, x) {
    override fun foo()
    {
        println("B.foo")
        println("y=$y")
        super.bar()
    }
}

abstract class A(val str: String = "", value: Int = 0) {
    var x: Int = value

    abstract fun foo()

    fun bar()
    {
        println("x=$x, str=$str")
        println("A.bar")
    }
}
```

>*abstract bir bir sınıftan türeyen sınıf en az bir tane abstract metodu override etmezse o da abstract bildirilmelidir*

```kotlin
package org.csystem.app

open class C : B() {
    override fun bar()
    {
        //...
    }
}

abstract class B : A() {
    override fun foo()
    {
        //...
    }
}

abstract class A(val str: String = "") {
    abstract fun foo()
    abstract fun bar()
}
```

>*abstract property elemanları*

```kotlin
package org.csystem.app

import kotlin.math.absoluteValue

fun main()
{
    val a: A = B()

    a.x = -5

    println("a.x = ${a.x}")
}

class B : A() {
    override var x: Int = 0
        set(value) {field = value.absoluteValue}
}

abstract class A {
    abstract var x: Int
}
```

>*Aşağıdaki örnekte A sınıfı içerisindeki x property elemanı "readonly" olduğundan error oluşur. Örnekte abstract bir readonly property elemanı read-write olarak override edilmiştir*

```kotlin
package org.csystem.app

import kotlin.math.absoluteValue

fun main()
{
    val a: A = B()

    a.x = -5 //error

    println("a.x = ${a.x}")

    val b = B()

    b.x = 10;
}


class B : A() {
    override var x: Int = 0
        set(value) {field = value.absoluteValue}
}

abstract class A {
    abstract val x: Int
}
```

>*Taban sınıfta var olarak bildirilmiş bir property elemanı türemiş sınıfta val olarak bildirilemez*

```kotlin
package org.csystem.app

class B : A() {
    override val x: Int = 0 //error:
}

abstract class A {
    abstract var x: Int
}
```

***Anahtar Notlar:*** Yukarıdaki örneklerden de anlaşılacağı gibi taban sınıftaki property elemanı read-write anlamında yükseltilebilir, ancak düşürülemez

>**Data classes (Veri Sınıfları):** \
>Bir sınıf data anahtar sözcüğü ile bildirildiğinde o sınıfın primary ctor elemanı olmak zorundadır. Bu ctor elemanında belirtilen parametreler ya val ya da var olarak bildirilmelidir. Yani data sınıflarının primary ctor elemanlarında property'ler bildirilmelidir. data sınıfları içerisinde bir takım metotlar otomatik olarak override edilmektedir. Örneğin toString metodu her bir property elemanı için toString metodunu çağırarak yazıyı oluşturur*

```kotlin
package org.csystem.app

fun main()
{
    val device = Device(1, "test", "192.168.2.123")

    println(device)
}

data class Device(val id: Int, var name: String, var ipAddress: String)
```

>*data sınıfları için yazılan metotların geneli istenirse progamcı tarafından da yazılabilir. Bu durumda artık derleyici metodu yazmaz*

```kotlin
package org.csystem.app

fun main()
{
    val device = Device(1, "test", "192.168.2.123")

    println(device)
}

data class Device(var id: Int, var name: String, var ipAddress: String) {
    override fun toString() = "{id : $id, name: $name, ipAddress: $ipAddress}"
}
```

>*data sınıflarının equals metodu da otomatik olarak override edilir.  Bu metot primary ctor ile belirtilen property elemanlarını karşılıklı olarak `==` operatörü ile karşılaştırır*

```kotlin
package org.csystem.app

fun main()
{
    val d1 = Device(1, "test", "192.168.2.123")
    val d2 = Device(1, "test", "192.168.2.123")

    println(if (d1 == d2) "Aynı cihaz" else "Farklı cihazlar")
    println(if (d1 != d2) "Farklı cihazlar" else "Aynı cihaz")
}

data class Device(var id: Int, var name: String, var ipAddress: String)
```

>*Complex sınıfı*

```kotlin
package org.csystem.app

import org.csystem.math.Complex

fun main()
{
    val z1 = Complex(2.3, 4.5)
    val z2 = Complex(2.3, 4.5)

    println(if (z1 == z2) "Aynı sayı" else "Farklı sayılar")
}
```

>*Aşağıdaki örnekte otomatik olarak yazılan equals metodunun ipAddress property elemanına bakması engellenmiştir. Ayrıca otomatik olarak override edilen toString metodu için yalnızca primary ctor'da bildirilen property elemanları kullanılır. Örnekteki toString metodunu kaldırarak sonucu gözlemleyiniz*

```kotlin
package org.csystem.app

fun main()
{
    val d1 = Device(1, "test", "192.168.2.34")
    val d2 = Device(1, "test", "192.168.2.35")

    println(if (d1 == d2) "Aynı cihaz" else "Farklı cihazlar")
    println(d1)
    println(d2)
}

data class Device(var id: Int, var name: String) {
    var ipAddress: String = ""

    constructor(id: Int, name: String, ipAddress: String) : this(id, name)
    {
        this.ipAddress = ipAddress
    }

    override fun toString() = "Device(id=$id, name=$name, ipAddress=$ipAddress)"
}
```

>*Yukarıdaki sınıf aşağıdaki gibi de yazılabilir*

```kotlin
package org.csystem.app

fun main()
{
    val d1 = Device(1, "test", "192.168.2.34")
    val d2 = Device(1, "test", "192.168.2.35")

    println(if (d1 == d2) "Aynı cihaz" else "Farklı cihazlar")
    println(d1)
    println(d2)
}

data class Device(var id: Int, var name: String, var ipAddress: String = "") {

    override fun toString() = "Device(id=$id, name=$name)"
    override fun equals(other: Any?) = other is Device && id == other.id && name == other.name
    //...
}

```

>*data sınıfları componentN fonksiyonlarına sahiptir. Bu fonksiyonlar bildirim sırasına göre primary ctor ile bildirilen property elemanlarına ilişkin değerleri döndürürler*

```kotlin
package org.csystem.app

import org.csystem.math.Complex

fun main()
{
    val z = Complex(3.5, 4.8)

    val re = z.component1()
    val im = z.component2()

    println("$re + $im * i")
}
```

>component fonksiyonlarına sahip olan sınıflar aşağıdaki gibi parçalanabilir

```kotlin
package org.csystem.app

import org.csystem.math.Complex
import kotlin.random.Random

fun main()
{
    val (re, im) = createRandomComplex(-10.0, 10.0)

    println("$re + $im * i")
}

fun createRandomComplex(min: Double, bound: Double, random: Random = Random) = Complex(random.nextDouble(min, bound), random.nextDouble(min, bound))

```

>*component fonksiyonlarına sahip olan sınıflar aşağıdaki gibi parçalanabilir*

```kotlin
package org.csystem.app

import org.csystem.math.Complex
import org.csystem.util.console.kotlin.readInt
import kotlin.random.Random

fun main()
{
    val n = readInt("Input a number:")

    for ((re, im) in createRandomComplexNumbers(n, -10.0, 10.0))
        println("$re + $im * i")
}

fun createRandomComplexNumbers(count: Int, min: Double, bound: Double, random: Random = Random) = Array<Complex>(count) { createRandomComplex(min, bound, random) }
fun createRandomComplex(min: Double, bound: Double, random: Random = Random) = Complex(random.nextDouble(min, bound), random.nextDouble(min, bound))

```


>*Parçalanabilen bir sınıf için sıra önemli olarak tüm parçalar alınmayabilir*

```kotlin
package org.csystem.app

fun main()
{
    val (id, name, surname) = createPerson("ali", 1)

    println("$name $surname, $id")
}

data class Person(val id:Int, var name:String, var surname:String, var address: String)

fun createPerson(name: String, id:Int) = Person(id, name, "veli","mecidiyeköy")
```


>*Parçalama işleminde alınması istenmeyen ara parçalar için `_` karakteri kullanılabilir*

```kotlin
package org.csystem.app

fun main()
{
    val (_, name, _, address,) = createPerson("ali", 1)

    println("$name, $address")
}

data class Person(val id:Int, var name:String, var surname:String, var address: String)

fun createPerson(name: String, id:Int) = Person(id, name, "veli","mecidiyeköy")
```

>***Sınıf Çalışması:** Klavyeden katsayıları girilen ikinci dereceden bir denklemin köklerini bulan programı yazınız*\
*Aşağıdaki örneği inceleyiniz:*

```kotlin
package org.csystem.app.math.equation.quadratic

import org.csystem.math.equation.quadratic.solveQuadraticEquation
import org.csystem.util.console.kotlin.readDouble

private fun runQuadraticEquationConsoleApp()
{
    while (true) {
        val a = readDouble("Input a:")

        if (a == 0.0)
            break

        val b = readDouble("Input b:")
        val c = readDouble("Input c:")

        val (x1, x2, exists) = solveQuadraticEquation(a, b, c)

        if (exists)
            println("x1 = $x1, x2 = $x2")
        else
            println("No real root")
    }
}


fun main() = runQuadraticEquationConsoleApp()


package org.csystem.math.equation.quadratic

data class QuadraticEquationResultInfo(val x1: Double, val x2: Double, val exits: Boolean)

package org.csystem.math.equation.quadratic

import kotlin.math.sqrt

private fun calculateDelta(a: Double, b: Double, c: Double) = b * b - 4 * a * c

fun solveQuadraticEquation(a: Double, b: Double, c: Double) : QuadraticEquationResultInfo
{
    val delta = calculateDelta(a, b, c)

    return when {
        delta >= 0 -> {
            val sqrtDelta = sqrt(delta)
            QuadraticEquationResultInfo((-b + sqrtDelta) / (2 * a), (-b - sqrtDelta) / (2 * a), true)
        }
        else -> QuadraticEquationResultInfo(Double.NaN, Double.NaN, false)
    }
}
```


>***Sınıf Çalışması:** Klavyeden katsayıları girilen ikinci dereceden bir denklemin köklerini bulan programı yazınız.*

>*Programcı data sınıfları için primary ctor'da bildirmediği property elemanları için de componentN fonksiyonlarını yazabilir. Bu fonksiyonlar aslında operatör fonksiyonu olduğundan operator anahtar sözcüğü ile bildirilmelidir. Aksi durumda fonksiyon operatör fonksiyonu olmaz. Bu durumda parçalama yapılamaz*

```kotlin
package org.csystem.app

import org.csystem.math.Complex
import org.csystem.util.console.kotlin.readInt
import kotlin.random.Random

fun main()
{
    val n = readInt("Input a number:")

    for ((re, im, _, conj) in createRandomComplexNumbers(n, -10.0, 10.0))
        println("$re + $im * i -> $conj")
}

fun createRandomComplexNumbers(count: Int, min: Double, bound: Double, random: Random = Random) = Array<Complex>(count) { createRandomComplex(min, bound, random) }
fun createRandomComplex(min: Double, bound: Double, random: Random = Random) = Complex(random.nextDouble(min, bound), random.nextDouble(min, bound))

```

>*data sınıfları open olarak bildirilemez. Dolayısıyla türetmeye kapalıdır*

```kotlin
package org.csystem.app

class MyPerson(name: String, id: Int) : Person(name, id)
data class Person(var name: String, var id: Int)
```

>*data sınıfı olamayan sınıflar için de componentN fonksiyonları yazılabilir. Bu durumda yine parçalama yapılabilir*

```kotlin
package org.csystem.app

fun main()
{
    val (id, name) = Person(1, "ali")

    print("$id, $name")
}

class Person(var id: Int, var name: String) {
    operator fun component1() = id
    operator fun component2() = name
    //...
}
```

>*data sınıflarının copy isimli metotları bulunur. copy metodu data sınıfının bir kopyasını çıkartır. copy metodu data sınıfı içersinde programcı tarafından yazılmaz.. copy metodu parametre olarak primary cotor'da bildirilen property elemanlarına ilişkin parametrelere sınıf property elemanlarını default argüman olarak geçecek şekilde yazılır.* 

>*Örnek bir temsili sınıf:*
 
```kotlin
class Person(var id: Int, var name: String) {
        fun copy(id: Int = this.id, name: String = this.name) = Person(id, name)
        //...
    } 
```

>*Dikkat yukarıdaki temsili sınıf bir data sınıfı olmadığı için diğer metotlar yazılmaz*

```kotlin
package org.csystem.app

fun main()
{
    val p = Person(1, "ali")
    val pcopy = p.copy()

    println(p == pcopy)     //true
    println(p === pcopy)    //false
    println(pcopy)

    val pcopy2 = p.copy(name = "Veli")

    println(pcopy2)
}

data class Person(var id: Int, var name: String)
```

>*Circle sınıfı data sınıfı olmamasına rağmen elemanları anlamlı olduğunda componentN fonksiyonları yazılmıştır*

```kotlin
package org.csystem.app

import org.csystem.kotlin.util.math.geometry.Circle

fun main()
{
    val (r, a, c) = Circle(3.4)

    println("r = $r, area = $a, circumference = $c")
}
```

>*Circle sınıfının copy metodu programcı tarafından yazılmıştır*

```kotlin
package org.csystem.app

import org.csystem.kotlin.util.math.geometry.Circle

fun main()
{
    val c = Circle(-3.4)
    val cc = c.copy()

    println(c)
    println(cc)
}

/*----------------------------------------------------------------------
	FILE        : Circle.kt
	AUTHOR      : Android-Mar-2023 Group
	LAST UPDATE : 22.05.2023

	Circle class that represents the circle in geometry

	Copyleft (c) 1993 by C and System Programmers Association
	All Rights Free
-----------------------------------------------------------------------*/
package org.csystem.math.geometry

import kotlin.math.abs

private const val DELTA = 0.00001

open class Circle(radius: Double = 0.0) {
    var radius: Double = abs(radius)
        set(value) {
            field = abs(value)
        }

    val area : Double
        get() = Math.PI * radius * radius

    val circumference : Double
        get() = 2 * Math.PI * radius

    fun copy() = Circle(radius)
    operator fun component1() = radius
    operator fun component2() = area
    operator fun component3() = circumference
    override fun hashCode() = radius.hashCode()
    override operator fun equals(other: Any?) = other is Circle && abs(radius - other.radius) < DELTA;
    override fun toString() = "Radius = %.2f, Area =  %.2f, Circumference = %.2f".format(radius, area, circumference)
}

```

>*Kotlin'de referans türlerine bile null atanamaz*

```kotlin
package org.csystem.app

fun main()
{
    var p: Person = null //error

}

class Person
```

>*Kotlin'de T bir tür olmak üzere `T?` nullable type olarak adlandırılır*

```kotlin
package org.csystem.app

fun main()
{
    var s : Person? = null
}

class Person
```

>*Kotlin'de temel türler sınıf olarak bildirildiğinden temel türlere ilişkin sınıflar türünden nullable referanslar da olabilir*

```kotlin
package org.csystem.app

fun main()
{
    var a : Int? = null

    //...
}
```

>*Kotlin'de nullable bir referans ile o referansa ilişkin sınıfın elemanlarına nokta operatörü erişilemez*

```kotlin
package org.csystem.app

fun main()
{
    val s : String? = "ankara"

    println(s.length) //error
}
```

>*Kotlin'de nullable bir referans ile `?.` operatörü kullanılarak elemanlara erişilebilir*

```kotlin
package org.csystem.app

fun main()
{
    val s : String? = "ankara"

    println(s?.length)
}
```

>*`?.` operatörünün kullanımında  hiç bir zaman NullPointerException (NPE) fırlatılmasına yol açmaz.* 
>
>*Aşağıdaki örneği inceleyiniz:*

```kotlin
package org.csystem.app

import org.csystem.util.console.kotlin.readInt
import org.csystem.util.string.kotlin.getRandomTextEN
import kotlin.random.Random

fun main()
{
    for (i in 1..readInt("Bir sayı giriniz:")) {
        val s: String? = randomText()
        val len: Int? = s?.length

        println(if (len != null) len else "text üretilemedi")
    }
}

fun randomText(random: Random = Random) = if (random.nextBoolean()) getRandomTextEN(10) else null
```

>***Elvis Operatörü:** Bu operatör aslında "null coalecing operatör"'dür. Ancak Elvis Presley'nin gözleri ve saçına benzetildiği için "Elvis operator" de denilmektedir*

```kotlin
package org.csystem.app

import org.csystem.util.console.kotlin.readInt
import org.csystem.util.string.kotlin.getRandomTextEN
import kotlin.random.Random

fun main()
{
    for (i in 1..readInt("Bir sayı giriniz:")) {
        val s: String? = randomText()
        val len: Int? = s?.length

        println(len ?: "text üretilemedi")
    }
}

fun randomText(random: Random = Random) = if (random.nextBoolean()) getRandomTextEN(10) else null
```

>*Aşağıdaki örnekte if deyiminin doğru kısmında a'nın null olamayacağını derleyici anlamış smart cast yapılmıştır*

```kotlin
package org.csystem.app

import org.csystem.util.array.kotlin.write
import org.csystem.util.console.kotlin.readInt
import kotlin.random.Random

fun main()
{
    val n = readInt("n:")
    val min = readInt("min:")
    val max = readInt("max:")
    val a = randomIntArray(n, min, max)

    if (a != null) {
        println("Length of an array:${a.size}")
        write(a)
    }
    else
        println("Invalid Values")
}

fun randomIntArray(n: Int, min: Int, max: Int) : IntArray?
{
    if (n <= 0 || min > max)
        return null

    val result = IntArray(n)

    for (i in result.indices)
        result[i] = Random.nextInt(min, max)

    return result
}
```

>*Aşağıdaki örnekte yine smart cast vardır*

```kotlin
package org.csystem.app

import org.csystem.util.console.kotlin.readInt
import kotlin.random.Random

fun main()
{
    for (i in 1..readInt("Input a number:")) {
        val a: A? = createRandomA()
        val b: B? = a as? B

        if (b != null)
            b.foo()
        else
            println("null pointer")
    }
}

fun createRandomA(random: Random = Random) : A?
{
    return when (random.nextInt(3)) {
        1 -> A()
        2 -> B()
        else -> null
    }
}
open class A {
    fun foo()
    {
        println("foo")
    }
}

class B : A() {
    val x = 10
}
```

> *`!!` operatörü nullable type'dan normal türe dönüşüm için kullanılabilir fakat NPE fırlatabilir*

```kotlin
package org.csystem.app

fun main()
{
    val a:Int? = null
    val b: Double = a!!.toDouble()

    println(b)
}
```

>*`!!` operatörü nullable type'dan normal türe dönüşüm için kullanılabilir fakat NPE fırlatabilir. Aşağıdaki kodda programcı createAByValue metoduna geçilen argümanlar ile birlikte null döndürmeyeceğini garanti atına aldığından NPE hiç oluşmaz. Örneği createAByValue metoduna null döndürebilecek değerleri de arguman verip sonucu gözlemleyiniz*

```kotlin
package org.csystem.app

import org.csystem.util.console.kotlin.readInt
import kotlin.random.Random

fun main()
{
    for (i in 1..readInt("Input a number:")) {
        val a: A = createAByValue(Random.nextInt(1, 3))!!

        a.foo()
    }
}

fun createAByValue(value: Int) : A?
{
    return when (value) {
        1 -> A()
        2 -> B()
        else -> null
    }
}
open class A {
    fun foo()
    {
        println("foo")
    }
}

class B : A() {
    val x = 10
}
```

>*kotlin.io paketi içerisinde bulunan readLine fonksiyonu String? türüne geri döner*

```kotlin
package org.csystem.app

fun main()
{
    print("Bir yazı giriniz:")
    val s = readLine()!!

    println(s.uppercase())
}
```

>*`!!` operatörü nullable type'dan normal türe dönüşüm için kullanılabilir fakat NPE fırlatabilir. Aşağıdaki kodda programcı createAByValue metoduna geçilen argümanlar ile birlikte null döndürmeyeceğini garanti atına aldığından NPE hiç oluşmaz. Örneği createAByValue metoduna null döndürebilecek değerleri de arguman verip sonucu gözlemleyiniz*

```kotlin
package org.csystem.app

import org.csystem.util.console.kotlin.readInt
import kotlin.random.Random

fun main()
{
    for (i in 1..readInt("Input a number:")) {
        val a: A = createAByValue(Random.nextInt(1, 3))!!

        a.foo()
    }
}

fun createAByValue(value: Int) : A?
{
    return when (value) {
        1 -> A()
        2 -> B()
        else -> null
    }
}
open class A {
    fun foo()
    {
        println("foo")
    }
}

class B : A() {
    val x = 10
}
```

>**Exception işlemleri:** *Programın çalışma zamanı sırasında oluşan genel olarak hatalı durumlara exception denir. Aslında bir exception hata durumuna ilişkin olmayabilir. Bu durum nadiren karşımıza çıkar. Yani daha çok programın çalışma zamnında oluşan hatalı durumlara denir. Bu durumlara karşılık programcının kod içerisinde akışı belirlemesine "exception handling" denir. Kotlin'de Java'dan farklı olarak checked ve unchecked exception kavramları yoktur. Kotlin'de exception işlemleri şu anahtar sözcüklerle yapılır:*    
	*throw, try, catch, finally*

>*throw deyiminin genel biçimi aşağıdaki gibidir: throw `<referans>` Burada referans Throwable sınıfından doğrudan ya da dolaylı olarak türetilmiş bir sınıf türünden olmaldır. Kotlin'de Throwable sınıfından Exception ve Error sınıfları türetilmiştir. Pratikte programclar Throwable sınıfından doğrudan türetme yapmazlar. Exception veya Error sınıfından doğrudan ya da dolaylı olarak türetme yaparlar. Error sınıfı ayrı bir throwable'dır. İleride ne için kullanıldığı ele alınacaktır. Java'da çok daha kritik öneme sahip olan RuntimeException isiml sınıftan da birçok sınıfı türetilmiştir. Throwable sınıfından doğrudan ya da dolaylı olarak türetilen sınıflara "exception classes" da denilmektedir.*

>*Bir kodun exception bakımından ele alınabilmesi için try bloğu içerisinde olması gerekir. try bloğunu bir veya birden fazla catch bloğu ile finally bloğu veya tek başına finally bloğu takip edebilir. Kotlin'de try hem deyim hem de ifsade biçiminde kullanılabilmektedir (try expression statment). Bir exceptiojn fırlatıldığında akış fırlatılan fonksiyondan bir daha geri dönmemek üzere (non-resumptive) çıkar. Akış bir try bloğu içerisinde o try bloğuna ilişkin catch bloklarına yukarıdan aşağıya sırasıyla kontrol eder. Uygun biri catch bloğu bulursa o catch bloğunu çalıştırır. Uygun catch bloğu, fırlatılan exception nesnesine ilişkin referansın atanabildiği (convert) türden catch parametresine sahip bloktur. Eğer hiç uygun blok bulamazsa, onu try bloğunu kapsayan try bloklarına ait catch bloklarına sırasıyla bakar, ilk bulduğu catch bloğunu çalıştırır. Diğer catch blokları çalıştırılmaz. Eğer hiç uygun catch bloğu bulunamazsa ve artık kapsayan hiç try bloğu kalmamışsa exception fırlatıldığı akış (thread) "abnormal" biri biçimde sonlanır. Akış try bloğundan nasıl çıkarsa çıksın finally bloğu çalıştıırılır. try bloğu hiç exception fırlatılmadan sonlanırsa tüm catch bloklarına atlanaran (varsa finally bloğu da çalıştırılarak) akış yoluna devam eder.*

>*Kotlin'de checked ve unchecked exception kavramları yoktur. Aşağıdaki örneğin Java karşılığı geçersizdir*

```kotlin
package org.csystem.app

import java.io.IOException

fun main()
{
    try {
        //...
    }
    catch (ex: IOException) {
        //...
    }
}
```

>*Aşağıdaki örneği inceleyiniz:*

```kotlin
package org.csystem.app

import org.csystem.util.console.kotlin.readDouble
import java.lang.NumberFormatException

fun main()
{
    var a = 0.0; var b = 0.0

    while (true) {
        try {
            print("Input the first number:")
            a = readln().toDouble()

            print("Input the second number:")
            b = readln().toDouble()

            println(divide(a, b))
        }
        catch (ex: NumberFormatException) {
            println("Invalid values")
        }
        catch (ex: IllegalArgumentException) {
            println("Reason:${ex.message}")
        }
        if (a == 0.0)
            break
    }
}

fun divide(a: Double, b: Double) : Double
{
    if (b == 0.0) {
        val msg = when (a) {
            0.0 -> "Undefined"
            else -> "Indeterminate"
        }

        throw IllegalArgumentException(msg)
    }

    return a / b
}
```

>*Aşağıdaki örneği inceleyiniz:*

```kotlin
package org.csystem.app

fun main()
{
    var a = 0.0; var b = 0.0

    try {
        while (true) {
            print("Input the first number:")
            a = readln().toDouble()

            print("Input the second number:")
            b = readln().toDouble()

            println(divide(a, b))

            if (a == 0.0)
                break
        }
    }
    catch (ex: NumberFormatException) {
        println("Invalid values")
    }
    catch (ex: IllegalArgumentException) {
        println("Reason:${ex.message}")
    }
    finally {
        println("Thanks")
    }
}

fun divide(a: Double, b: Double) : Double
{
    if (b == 0.0) {
        val msg = when (a) {
            0.0 -> "Undefined"
            else -> "Indeterminate"
        }

        throw IllegalArgumentException(msg)
    }

    return a / b
}
```

>*Klavyeden geçerli değer okuyan fonksiyonlar. Fonksiyonlar SampleKotlin projesi içerisindeki console.kt dosyasına bakınız*

```kotlin
package org.csystem.app

import org.csystem.util.console.kotlin.readInt

fun main()
{
    val a = readInt("Birinci sayıyı giriniz:", "Hatalı giriş yaptınız:")
    val b = readInt("İkinci sayıyı giriniz:", "Hatalı giriş yaptınız:")

    println(a * b)
}
```

>*Klavyeden geçerli değer okuyan fonksiyonlar. Fonksiyonlar SampleKotlin projesi içerisindeki console.kt dosyasına bakınız*

```kotlin
package org.csystem.app

import org.csystem.util.console.kotlin.readInt

fun main()
{
    val a = readInt("Birinci sayıyı giriniz:", "Hatalı giriş yaptınız:", "\n")
    val b = readInt("İkinci sayıyı giriniz:", "Hatalı giriş yaptınız:", "\n")

    println(a * b)
}
```

>*Aşağıdaki örneği inceleyiniz:*

```kotlin
package org.csystem.app

import org.csystem.util.console.kotlin.readDouble

fun main()
{
    var a = 0.0; var b = 0.0

    while (true) {
        try {
            a = readDouble("Input the first number:", "Invalid Value!...")
            b = readDouble("Input the second number:", "Invalid value!...")

            println(divide(a, b))
        }

        catch (ex: IllegalArgumentException) {
            println("Reason:${ex.message}")
        }
        if (a == 0.0)
            break
    }
}

fun divide(a: Double, b: Double) : Double
{
    if (b == 0.0) {
        val msg = when (a) {
            0.0 -> "Undefined"
            else -> "Indeterminate"
        }

        throw IllegalArgumentException(msg)
    }

    return a / b
}
```

>*Kotlin'de bir User Defined Type'a (sınıf, interfacec vb.) ve temel türlere ilişkin bir sınıfa programcı fonksiyonlar ekleyebilir. Yani adeta sınıf bildirimine ekleme yapmış gibi fonksiyonlar yazılabilir. Bu tarz fonksiyonlara "extension functions" denir*

>*Extension (eklenti) fonksiyonlar*

```kotlin
package org.csystem.app

import org.csystem.util.console.kotlin.readInt

fun main()
{
    while (true) {
        val a = readInt("Bir sayı giriniz:")

        println(a.square())
        if (a == 0)
            break
    }
}

fun Int.square() = this * this
```

> *Extension (eklenti) fonksiyonlar*

```kotlin
package org.csystem.app

import org.csystem.util.numeric.isPrime

fun main()
{
    for (n in 0L..100)
        if (n.isPrime())
            print("$n ")

    println()
}
```

>*Extension (eklenti) fonksiyonlar*

```kotlin
package org.csystem.util.array.kotlin.test

import org.csystem.util.array.kotlin.randomIntArray
import org.csystem.util.array.kotlin.write
import org.csystem.util.console.kotlin.readInt
import kotlin.random.Random

fun main() = runRandomIntArrayTest()

fun runRandomIntArrayTest()
{
    while (true) {
        val count = readInt("Dizinin eleman sayısını giriniz:")

        if (count <= 0)
            break
        val a = Random.randomIntArray(count, 0, 100)

        a.write(2)
    }

    println("Tekrar yapıyor musunuz?")
}
```

>***infix fonksiyonlar:***  *Fonksiyonlar infix fonksiyon olarak bildirilebilir. infix fonksiyonlar bir parametreli extension fonksiyon veya sınıfın üye fonksiyonu (metot) olmalıdır. Aşağıdaki örnek infix fonksiyonu göstermek için yazılmıştır. infix fonksiyonlar iki operandlı bir operatör biçiminde kullanılabilir. Bu durumda fonksiyon ismi operandlarının arasında kullanılabilir. Bu durumda fonksiyonun geri dönüş değeri infix olarak yazılan ifadenin değeri. Yani yine fonksiyon çağrılmış olur.*

```kotlin
package org.csystem.app

import org.csystem.util.console.kotlin.readInt

fun main()
{
    val a = readInt("Bir sayı giriniz:")
    val b = readInt("Bir sayı daha giriniz:")

    println(a add b)
    println(a.add(b))
    val op = Operation(a)

    println(op add b)
    println(op.add(a))
}

infix fun Int.add(b: Int) = this + b

class Operation(val a: Int) {
    infix fun add(b: Int) = a + b
}
```

>***Anahtar Notlar:*** *Her iki parametreli fonksiyon infix fonksiyon olarak yazılmalı mıdır? Bu tamamen fonksiyona bağlıdır. Gereksiz yere infix fonksiyon yazmak veya infix olması iyi bir tasarım olan fonksiyonu infix yapmamak kötü teknik olarak düşünülebilir*

>***Operatör Fonksiyonları:** Anımsanacağı gibi Kotlin'de hemen hemen tüm operatörler bir fonksiyona karşılık gelir. Temel türlere ilişkin çeşitli operatör fonksiyonlar tanımlanmıştır. Programcı bildirdiği bir sınıf için de anlamlı olan operatörlere ilişkin fonksiyonları yazabilir. Örneğin Strşng sınıfının + operatörüne ilişkin plus fonksiyonları ile "string concat" yapılabilmektedir. Yine benzer şekilde equals fonksiyonu override edildiği için Strinbg'lerin özdeşlik karşılaştırması == ve != operatörleri ile yapılabilmektedir. Hatta bu anlamda Java'da yazılmış olan ve equals metodunu override eden sınıflar türünden nesnelerin mantıksal eşitlik karşılıkları == ve != operatörleri ile yapılabilmektedir.*

>*Programcı yazdığı sınıfa operatör fonksiyonlarını anlamsız olacak şekilde eklememelidir. Örneğin bir tarih işlemi yapan sınıf yazıldığında iki tarihin toplanması anlamsızdır ancak birbirinden çıkartılması anlamlıdır. Bu durumda programcı bu sınıf için minus operatör fonksiyonuu yazar ancak plus operatör fonksiyonunu yazmaz*

>*MutableComplex sınıfının operatör fonksiyonları*

```kotlin
package org.csystem.app

import org.csystem.math.random.nextMutableComplex
import kotlin.random.Random

fun main()
{
    val z1 = Random.nextMutableComplex(-10, 10)
    val z2 = Random.nextMutableComplex(-10, 10)
    val z = z1 + z2

    println(z1)
    println(z2)
    println(z)
}
```

>*MutableComplex sınıfının operatör fonksiyonları*

```kotlin
package org.csystem.app

import org.csystem.math.random.nextMutableComplex
import kotlin.random.Random

fun main()
{
    val z1 = Random.nextMutableComplex(-10, 10)
    val value = Random.nextDouble(10.0)
    val z = z1 + value

    println(z1)
    println("value = $value")
    println(z)
}
```

>*MutableComplex sınıfının operatör fonksiyonları*

```kotlin
package org.csystem.app

import org.csystem.math.random.nextMutableComplex
import kotlin.random.Random

fun main()
{
    val z1 = Random.nextMutableComplex(-10, 10)
    val z2 = Random.nextMutableComplex(-10, 10)
    val z = z1 - z2

    println(z1)
    println(z2)
    println(z)
}
```

>*MutableComplex sınıfının operatör fonksiyonları*

```kotlin
package org.csystem.app

import org.csystem.math.random.nextMutableComplex
import kotlin.random.Random

fun main()
{
    val z = Random.nextMutableComplex(-10, 10)
    val value = Random.nextDouble(10.0)
    val result = z + value

    println(z)
    println("value = $value")
    println(result)
}
```

>*MutableComplex sınıfının operatör fonksiyonları*

```kotlin
package org.csystem.app

import org.csystem.math.plus
import org.csystem.math.random.nextMutableComplex
import kotlin.random.Random

fun main()
{
    val z = Random.nextMutableComplex(-10, 10)
    val value = Random.nextDouble(10.0)
    val result = value + z

    println(z)
    println("value = $value")
    println(result)
}
```

>*MutableComplex sınıfının operatör fonksiyonları*

```kotlin
package org.csystem.app

import org.csystem.math.minus
import org.csystem.math.random.nextMutableComplex
import kotlin.random.Random

fun main()
{
    val z = Random.nextMutableComplex(-10, 10)
    val value = Random.nextDouble(10.0)
    val result = value - z

    println(z)
    println("value = $value")
    println(result)
}
```

>*MutableComplex sınıfının operatör fonksiyonları*

```kotlin
package org.csystem.app

import org.csystem.math.random.nextMutableComplex
import kotlin.random.Random

fun main()
{
    val z = Random.nextMutableComplex(-10, 10)
    val value = Random.nextDouble(10.0)
    val result = z - value

    println(z)
    println("value = $value")
    println(result)
}
```

>*MutableComplex sınıfının operatör fonksiyonları*

```kotlin
package org.csystem.app

import org.csystem.math.random.nextMutableComplex
import kotlin.random.Random

fun main()
{
    val z = Random.nextMutableComplex(-10, 10)
    val result = -z

    println(z)
    println(result)
}
```

>*`++` (inc) ve `--`(dec) operatör fonksiyonları yazılırken nesne için artırma/azaltma yapılmamalıdır. Artırılmış/azaltılmış yeni bir nesnenin referansına dönülmelidir. Derleyici uygun kodları üreterek bu operatörlerin prefix ve postfix olarak doğru kullanılmalarını sağlar*

```kotlin
package org.csystem.app

import org.csystem.math.random.nextMutableComplex
import kotlin.random.Random

fun main()
{
    var z = Random.nextMutableComplex(-10, 10)

    println(z)
    val result = ++z

    println(z)
    println(result)
}
```

```kotlin
package org.csystem.app

import org.csystem.math.random.nextMutableComplex
import kotlin.random.Random

fun main()
{
    var z = Random.nextMutableComplex(-10, 10)

    println(z)
    val result = z++

    println(z)
    println(result)
}
```

```kotlin
package org.csystem.app

import org.csystem.math.random.nextMutableComplex
import kotlin.random.Random

fun main()
{
    var z = Random.nextMutableComplex(-10, 10)

    println(z)
    val result = --z

    println(z)
    println(result)
}
```

```kotlin
package org.csystem.app

import org.csystem.math.random.nextMutableComplex
import kotlin.random.Random

fun main()
{
    var z = Random.nextMutableComplex(-10, 10)

    println(z)
    val result = z--

    println(z)
    println(result)
}
```

>*Anımsanacağı gibi data sınıflarının equals operatör fonksiyonu primary ctor'da bildirilmiş karşılıklı property elemanları için `==` operatörü ile karşılaştırma yapacak şekilde override edilmiştir*

```kotlin
package org.csystem.app

import org.csystem.kotlin.util.math.MutableComplex

fun main()
{
    val z1 = MutableComplex(3.0, 4.0)
    val z2 = MutableComplex(3.0, 4.0)

    println(if (z1 == z2) "Aynı sayı" else "Farklı sayılar")
    println(if (z1 != z2) "Farklı sayılar" else "Aynı sayı")
}
```

>*Aşağıdaki örnekte MutableComplex sınıfında olmayan (anlamlı da olmayan) karşılaştırma operatörlerine ilişkin compareTo fonksiyonu extension olarak "müşteri kod (client code)" tarafından yazılmıştır*

```kotlin
package org.csystem.app

import org.csystem.math.MutableComplex
import kotlin.math.abs

const val delta = 0.00001

operator fun MutableComplex.compareTo(other: MutableComplex) : Int
{
    val diff = norm - other.norm

    return when {
        abs(diff) < delta -> 0
        diff > 0 -> 1
        else -> -1
    }
}

fun main()
{
    val z1 = MutableComplex(3.0, 4.0)
    val z2 = MutableComplex(3.0, 4.0)

    println(z1 <= z2)
    println(z1 < z2)
    println(z1 >= z2)
    println(z1 > z2)
}
```

>*Kotlin'de fonksiyon çağırma operatör fonksiyonu overload edilebilir. Bu durumda ilgili sınıf türünden referans ismi fonksiyon ismi gibi kullanılabilir. Bu operatör fonksiyonunun ismi invoke'dur. Fonksiyon çağırma operatör fonksiyonu overload edilmiş sınıflara programlamada "functor/function object" de denilmektedir. Bu operatör fonksiyonu bazı durumlarda callback/callable alan fonksiyonlarda (high order functions (HOF)) kullanılabilmektedir. HOF'lar ileride ele alınacaktır*

```kotlin
package org.csystem.app

import org.csystem.math.MutableComplex
import org.csystem.math.random.nextMutableComplex
import kotlin.random.Random

operator fun MutableComplex.invoke() = println(this)

fun main()
{
    val z = Random.nextMutableComplex(-10, 10)

    z()
    println(z(2.3, 4.5))
    z()
    println(z(8.9))
    z()
}
```

>*MutableComplex sınıfı*

```kotlin
package org.csystem.app



import org.csystem.math.MutableComplex
import org.csystem.math.random.nextMutableComplex
import kotlin.random.Random

operator fun MutableComplex.not() = this.conjugate

fun main()
{
    val z = Random.nextMutableComplex(-10, 10)

    println(z)
    println(!z)
}
```

>*Sınıfın get ve/veya set operatör fonksiyonları overload edildiğinde o sınıf türünden bir referans `[]` operatörü ile de kullanılabilir. Bu tarz kullanıma ilişkin fonksiyonlara bazı kaynaklar "indexer" da denmektedir. Bu terim Kotlin'e özgü değildir*

```kotlin
package org.csystem.app

import org.csystem.math.random.nextMutableComplex
import kotlin.random.Random

fun main()
{
    val z = Random.nextMutableComplex(-10, 10)

    println(z)
    ++z[0]
    println("${z[0]}")
    println(z)
    --z[1]
    println("${z[1]}")
    println(z)
}
```

```kotlin
/*----------------------------------------------------------------------
	FILE        : MutableComplex.kt
	AUTHOR      : Android-Mar-2023 Group
	LAST UPDATE : 29.05.2023

	MutableComplexclass that represents the complex number

	Copyleft (c) 1993 by C and System Programmers Association
	All Rights Free
-----------------------------------------------------------------------*/
package org.csystem.math

import java.lang.IndexOutOfBoundsException
import kotlin.math.sqrt

private fun plus(a1: Double, b1: Double, a2: Double, b2: Double) = MutableComplex(a1 + a2, b1 + b2)
private fun minus(a1: Double, b1: Double, a2: Double, b2: Double) = plus(a1, b1, -a2, -b2)
private fun times(a1: Double, b1: Double, a2: Double, b2: Double) : MutableComplex
{
    TODO()
}

private fun div(a1: Double, b1: Double, a2: Double, b2: Double) : MutableComplex
{
    TODO()
}

operator fun Double.plus(z: MutableComplex) = plus(this, 0.0, z.real, z.imag)
operator fun Double.minus(z: MutableComplex) = minus(this, 0.0, z.real, z.imag)
operator fun Double.times(z: MutableComplex) = times(this, 0.0, z.real, z.imag)
operator fun Double.div(z: MutableComplex) = div(this, 0.0, z.real, z.imag)

data class MutableComplex(var real: Double = 0.0, var imag: Double = 0.0) {
    val norm: Double
        get() = sqrt(real * real + imag * imag)

    val length: Double
        get() = norm

    val conjugate: MutableComplex
        get() = MutableComplex(real, -imag)

    operator fun component3() = norm
    operator fun component4() = conjugate

    operator fun plus(other: MutableComplex) = plus(real, imag, other.real, other.imag)
    operator fun plus(value: Double) = plus(real, imag, value, 0.0)

    operator fun minus(other: MutableComplex) = minus(real, imag, other.real, other.imag)
    operator fun minus(value: Double) = minus(real, imag, value, 0.0)

    operator fun times(other: MutableComplex) = times(real, imag, other.real, other.imag)
    operator fun times(value: Double) = times(real, imag, value, 0.0)

    operator fun div(other: MutableComplex) = div(real, imag, other.real, other.imag)
    operator fun div(value: Double) = div(real, imag, value, 0.0)

    operator fun unaryMinus() = MutableComplex(-real, -imag)
    operator fun unaryPlus() = copy()
    operator fun inc() = MutableComplex(real + 1, imag)
    operator fun dec() = MutableComplex(real - 1, imag)

    operator fun invoke(real: Double, imag: Double = 0.0) : MutableComplex
    {
        val old = MutableComplex(this.real, this.imag)
        this.real = real
        this.imag = imag

        return old
    }

    operator fun get(index: Int) : Double
    {
        if (index < 0 || index > 1)
            throw IndexOutOfBoundsException("index must be zero or one")

        return if (index == 0) real else imag
    }

    operator fun set(index: Int, value: Double)
    {
        if (index < 0 || index > 1)
            throw IndexOutOfBoundsException("index must be zero or one")
        if (index == 0)
            real = value
        else
            imag = value
    }

    override fun toString() = "(%.2f, %.2f)".format(real, imag)
}
```

>*Aşağıdaki örnekte Path isimli bir yol ifadesi üzerinde işlem yapan basit bir sınıf yazılmıştır. Detayları gözardı etmek koşuluyla div operatör fonksiyonuna yüklenen göreve dikkat ediniz*

```kotlin
package org.csystem.app

fun main()
{
    var filePath1 = FilePath("/home/oguz/study")
    var filePath2 = FilePath("/home/oguz/study/")
    filePath1 /= "names.txt"
    filePath2 += "names.txt"

    println(filePath1)
    println(filePath2)
}

class FilePath(var path: String) {
    operator fun div(filePath: FilePath) = this / filePath.toString()
    operator fun div(path: String) = FilePath("${this.path}/$path")
    operator fun plus(path: String) = FilePath("${this.path}$path")
    operator fun plus(filePath: FilePath) = this + filePath.toString()
    override fun toString() = path
    //...
}
```

>*Aşağıdaki örneği inceleyiniz*

```kotlin
package org.csystem.app

fun main()
{
    var vec = Vector3(2.4F, 6.7F, 0F)
    println(vec)
    vec *=  3F
    println(vec)
}

data class Vector3(val x: Float, val y: Float, val z: Float) {
    operator fun times(value: Float) = Vector3(x * value, y * value, z * value)
    //...
}
```

>***Interface:*** \
>*Interface bildirimi yine bir tür bildirimidir (user defined type). interface nesne özelliği göstermez. interface içerisinde gövdesiz yazılan metotlar abstract olarak bildirilmiş olur. Bu anlamda interface'ler abstract sınıflara benzerler. Bir interface'den bir sınıf türetilmezi, bir sınıf bir interface'i destekler (implementation). Interface'ler ile "multiple inheritance" da belirli ölçüde desteklenmiş olur*

>*interface bildirimi interface anahtar sözcüğü ile yapılır. interface ismini okunabilirlik açısından I ile başlatacağız. Kotlin'deki standart interface'lerde bu convention'a uyulmamıştır*

```kotlin
package org.csystem.app

fun main()
{
    val a = A()

    doWork(a)
}

fun doWork(ix: IX)
{
    ix.foo()
    println(ix.value)
    ix.bar()
}

class A : IX {
    override  fun foo()
    {
        println("foo")
    }

    override var value : Int = 10
}

interface IX {
    fun foo()

    fun bar()
    {
        println("bar")
    }

    var value: Int
}
```

>*Bir sınıf birden fazla interface'i implemente etmişse farklı interface'lerdeki aynı metotlar için bir tane metot yazılması yeterlidir*

```kotlin
package org.csystem.app

fun main()
{
    val a = A()
    val ix: IX = a

    ix.foo()
    ix.bar()
    val iy: IY = a

    iy.foo()
}

open class Sample

class A : Sample(), IX, IY {
    override fun foo()
    {
        println("foo")
    }

    override  fun bar()
    {
        println("bar")
    }
}

interface IX {
    fun foo()
    fun bar()
}

interface IY {
    fun foo()
}
```

> *"super<tür ismi>" sentaksı ile taban türlere (sınıf, interface vb.) ilişkin istenilen default metot çağrılabilir*

```kotlin
package org.csystem.app

fun main()
{
    val a = A()

    a.bar()
}

class A : IX, IY {
    override  fun foo()
    {
        println("foo")
    }

    override fun bar()
    {
        println("bar")
        super<IX>.bar()
        super<IY>.bar()
    }
}

interface IX {
    fun foo()
    fun bar() //default metot
    {
        println("IX.bar")
    }
}

interface IY {
    fun foo()
    fun bar() //default metot
    {
        println("IY.bar")
    }
}
```

>super<tür ismi> sentaksı ile taban türlere (sınıf, interface vb.) ilişkin istenilen default metot çağrılabilir

```kotlin
package org.csystem.app

fun main()
{
    val a = A()

    a.bar()
}

class A : B(), IX, IY {
    override fun foo()
    {
        println("foo")
    }

    override fun bar()
    {
        println("bar")
        super<B>.bar()
        super<IX>.bar()
        super<IY>.bar()
    }
}

open class B {
    open fun bar()
    {
        println("B.bar")
    }
}

interface IX {
    fun foo()
    fun bar() //default metot
    {
        println("IX.bar")
    }
}

interface IY {
    fun foo()
    fun bar() //default metot
    {
        println("IY.bar")
    }
}
```

> *Aşağıdaki örnekte `**` ile belirtilen çağrıda açısal parantez kullanılmalıdır. Çünkü taban arayüzler içerisinde çağrılan metot ile aynı olan metot bulunmaktadır.* 

 ***Anahtar Notlar:*** Java'da super referansı nesnenin taban sınıf kımının adresi anlamına gelir, desteklediği imterface'ler ile bir bağlantısı yoktur. Ancak Kotlin'de super aynı zamanda desteklediği interface'ler için de kullanılır. Zaten bu sebeple açısal parantez kullanımı da dile eklenmiştir

```kotlin
package org.csystem.app

fun main()
{
    val a = A()

    a.bar()
}

class A : B(), IX, IY {
    override fun foo()
    {
        println("foo")
    }

    override fun bar()
    {
        println("bar")
        super<B>.bar() //**
    }
}

open class B {
    open fun bar()
    {
        println("B.bar")
    }
}

interface IX {
    fun foo()
    fun bar() //default metot
    {
        println("IX.bar")
    }
}

interface IY {
    fun foo()
    fun bar() //default metot
    {
        println("IY.bar")
    }
}
```

>*Aşağıdaki örnekte `**` ile belirtilen çağrıda açısal parantez kullanılmasına gerek yoktur. Çünkü taban arayüzler içerisinde çağrılan metot ile aynı olan metot bulunmamaktadır. Ancak sentaks olarak açısal parantez kullanımı da geçerlidir*

```kotlin
package org.csystem.app

fun main()
{
    val a = A()

    a.bar()
}

class A : B(), IX, IY {
    override fun foo()
    {
        println("foo")
    }

    override fun bar()
    {
        println("bar")
        super.bar() //**
    }
}

open class B {
    open fun bar()
    {
        println("B.bar")
    }
}

interface IX {
    fun foo()
}

interface IY {
    fun foo()
}
```

>*Aşağıdaki örnekte `**` ile belirtilen çağrıda açısal parantez kullanılmalıdır. IX içerisindeki bar metodunun private olması bu durumu etkilemez*

```kotlin
package org.csystem.app

fun main()
{
    val a = A()

    a.bar()
}

class A : B(), IX, IY {
    override fun foo()
    {
        println("foo")
    }

    override fun bar()
    {
        println("bar")
        super<B>.bar() //**
    }
}

open class B {
    open fun bar()
    {
        println("B.bar")
    }
}

interface IX {
    fun foo()

    private fun bar()
    {
        //...
    }
}

interface IY {
    fun foo()
}
```

***Anahtar Notlar:*** interface'lerin default metotları yani gövdesi olan metotları olabilir. Bu metotlar sanaldır ancak abstract değildir. Bu durum Java ve Kotlin için de geçerlidir

>*Kullanılabilmesi için mantıksal olarak açılması (open) gereken ve kullanım sonunda mantıksal olarak kapatılması gereken bir kaynağın (resource) bir sınıf tarafından kullanılnası durumunda bu sınıf bir convention olarak Closeable arayüzünü destekler. Başka bir deyişle Closeable arayüzünü destekleyen bir sınıf için böylesi bir kullanum söz konusudur algılanır. Closeable arayüzünün close isimli bir metodu bulunur. Yani kaynağı kullanan sınıfta bu arayüzün close metodunu kaynağın kullanımını serbest bırakacak şekilde override edilir. Bunu kullanan programcı da close çağırması gerektiğini bilir ve çağırır. Kotlin'de Closeable arayüzünü destekleyen sınıfların use isimli extension fonksiyonları vardır. Bu fonksiyon başka bir fonksiyonu callback olarak alır. Callback kavramı ileride detaylandırılacağından ilgili konuya gelene kadar use kullamını bir kalıp biçiminde düşüneceğiz. use fonksiyonu çağrısı bitikten sonra o sınıf için close fonksiyonu otomatik olarak çağrılır. Bu durumda programcının ayrıca close çağırması gerekmez*

>*use extension fonksiyonu. Bu, aslında Java'daki try-with-resources deyiminin Kotlin'deki karşılığıdır. Aşağıdaki kodun Java karşılığı:*

```java
try (Sample s = new Sample()) {
        s.foo(-10);
    }
```

```kotlin
package org.csystem.app

import java.io.Closeable
import java.lang.IllegalArgumentException
import kotlin.random.Random

fun main()
{
    val s = Sample()

    try {
        s.use {
            s.foo(Random.nextInt(-10, 10))
        }
    }
    catch (ex: IllegalArgumentException) {
        println(ex.message)
    }
}

class Sample : Closeable {
    fun foo(a: Int) {
        if (a < 0)
            throw IllegalArgumentException("a must be positive")

        println("foo")
    }
    override fun close()
    {
        println("close")
    }
}
```

>*Aşağıdaki örnekte exception oluşsa bile (exception yakalanamayacak) close çağrılır*

```kotlin
package org.csystem.app

import java.io.Closeable
import java.lang.IllegalArgumentException
import kotlin.random.Random

fun main()
{
    val s = Sample()

    s.use {
        s.foo(Random.nextInt(-10, 10))
    }
}

class Sample : Closeable {
    fun foo(a: Int) {
        if (a < 0)
            throw IllegalArgumentException("a must be positive")

        println("foo")
    }
    override fun close()
    {
        println("close")
    }
}
```

>*Bir dosyanın verileri (byte'ları) üzerinde işlem yapabilmek için işletim sisteminin çekirdeği düzeyinde bir takım işlemlerin yapılması gerekir. Bu işlemlere mantıksal olarak "dosyayı açmak" denir. Bir dosya ile ilgili işlemler bittiğinde yine çekirdek düzeyin yapılması gereken işlemler vardır. Buna da mantıksal olarak "dosyayı kapatmak" denir. JavaSE'de bulunan dosya işlemlerine yönelik (aslında IO işlemlerine yönelik) sınıfların bir çoğu Closeable arayüzünü destekler. Bu durumda programcının dosyayı kapatmak için close fonksiyonunu çağırması veya use fonbksiyonunu kullanması gerekir. Aşağıdaki birinci örnekte bir dosyaya klavyeden girilen yazılar satır satır eklenmektedir. İkinci örnekte ise dosyadan satır satır veriler okunmaktadır. Uygulamaya yönelik detaylar şu an önemsizdir. Yalnızca use fonksiyonunun kullanımına odaklanınız*

>***Not:** Örnekleri test etmek için ilk önce yazma yapan programı çalıştırınız*

```kotlin
package org.csystem.app

import java.io.BufferedWriter
import java.io.FileWriter
import java.io.IOException
import java.nio.charset.StandardCharsets

fun main() = runWriterApplication()

fun runWriterApplication()
{
    val fileName = "cities.txt"

    try {
        BufferedWriter(FileWriter(fileName, StandardCharsets.UTF_8, true)).use {
            while (true) {
                print("Input text:")
                val str = readln()

                if ("quit" == str)
                    break

                it.write("$str\r\n")
            }
            it.flush()
        }
    }
    catch (ignore: IOException) {
        println("IO Problem occurs")
    }
    catch (ignore: Throwable) {
        println("Problem occurs")
    }
}

package org.csystem.app

import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.nio.charset.StandardCharsets

fun main() = runReaderApplication()

fun runReaderApplication()
{
    val fileName = "cities.txt"

    try {
        BufferedReader(FileReader(fileName, StandardCharsets.UTF_8)).use {
            while (true) {
                val str = it.readLine() ?: break

                println(str)
            }
        }
    }
    catch (ignore: IOException) {
        println("IO Problem occurs")
    }
    catch (ignore: Throwable) {
        println("Problem occurs")
    }
}
```

>***Generics:***   
>*Kotlin'de ve Java'da generics derleme zamanında çok biçimli kodlar yazmak için kullanılır. Bu anlamda derleme zamanında türden bağımsız kod yazılabilmektedir. Java ve Kotlin'de generic kavramı temelde aynı amaçta olsa da birçok farklılığı da buklunmaktadır.Burada Kotlin'de generic kavramı ele alınacaktır. Generic kavramı genel olarak generic türler ve generic fonksiyonlar olmak üzere iki gruba ayrılabilir*

>*Generic sınıflar*

```kotlin
package org.csystem.app

fun main()
{
    val s1 = Sample<Int>(10)
    val s2 = Sample<Double>(4.5)
    val s3 = Sample<String>("csd")

    println(s1.x)
    println(s2.x)
    println(s3.x)
}

class Sample<T>(var x: T)

```

>*Aşağıdaki örnekte açılım (instantiation) yapılmadığı halde generic tür tespit edilebildiğinden (type inference/deduction) sorun oluşmaz*

```kotlin
package org.csystem.app

fun main()
{
    val s1 = Sample(10)
    val s2 = Sample(4.5)

    println(s1.x)
    println(s2.x)
}

class Sample<T>(var x: T)
```

>*Generic sınıflar için nesne yaratılırken generic parametrelerin türleri tespit edilemezse açılım kesinlikle kullanılmalıdır. Aksi durumda error oluşur*

```kotlin
package org.csystem.app

fun main()
{
    val s1 = Sample<Int>()
    val s2 = Sample<Double>()

    s1.foo(10)
    s2.foo(10.7)
}

class Sample<T> {
    //...
    fun foo(t: T) = println(t)
}
```

>*Referans bildiriminde açılım kullanılmalıdır. Tür tespiti dışında generic sınıflar açısal parantezsiz kullanılamaz. Bilindiği gibi Java' da generic bir sınıf açılımsız kullanıldığında generic parametre yerine Object geçmiş olur ki bu durum da Java'da tavsiye edilen bir kullanımm değildir*

```kotlin
package org.csystem.app

fun main()
{
    val s: Sample<String>

    s = Sample("ankara")

    println(s.x)
}

class Sample<T>(var x: T)
```

>*Referans bildiriminde açılım kullanılmalıdır*

```kotlin
package org.csystem.app

fun main()
{
    val s: Sample<String> = Sample("ankara")

    println(s.x)
}

class Sample<T>(var x: T)
```

>*Aşağıdaki örnekte Kotlin invariant özelliğe sahip olduğundan error oluşur*

```kotlin
package org.csystem.app

import java.lang.*

fun main()
{
    val s = Sample(Integer.valueOf(10));

    foo(s) //error
}

fun foo(s: Sample<Number>)
{
    println(s.x.toShort())
}

class Sample<T>(var x: T)
```

>*Generic bir sınıfın generic parametresi out ile bildirildiğinde covariant özellik kullanılabilir*

```kotlin
package org.csystem.app

import java.lang.*

fun main()
{
    val s = Sample(Integer.valueOf(10));

    foo(s)
}

fun foo(s: Sample<Number>)
{
    println(s.x.toShort())
}

class Sample<out T>(val x: T)
```

>*Aşağıdaki örnekte sınıf contravariant değildir. Açılım contravariant yapılmıştır*

```kotlin
package org.csystem.app

import java.lang.*

fun main()
{
    val s = Sample<Number>();

    foo(s)
}

fun foo(s: Sample<in Integer>)
{
    println(s.x)
}

class Sample<T>(var x: T? = null)
```

>*Aşağıdaki örnekte sınıf contravariant yapılmıştır*

```kotlin
package org.csystem.app

import java.lang.*

fun main()
{
    val s = Sample<Number>();

    foo(s)
}

fun foo(s: Sample<Integer>)
{
    println(s)
}

class Sample<in T> {
    //...
}
```

>*out ve in anahtar sözcükleri eğer sınıfın bildiriminde yazılacaksa sınıfın property elemanları immutable olmalıdır*

```kotlin
package org.csystem.app

fun main()
{
    val s = Sample(65)

    val m = Mest();

    m.foo(s)
}

class Mest {
    fun foo(s: Sample<Number>)
    {
        println(s.x.toChar())
    }
}
class Sample<out T>(var x: T) //error
```

>*Generic parametrelere kısıtlar verilebilir. Aşağıdaki örnekte Sample sınıfının T generic parametresinin türünün Closeable arayüzünü destekkleyen bir tür ile açılımı zorunludur. Bu kısıta aynı zamanda üstten sınır (upper bound) da denilmektedir*

```kotlin
package org.csystem.app

import java.io.Closeable
import java.io.FileOutputStream

fun main()
{
    val s = Sample<FileOutputStream, Int>()

    s.bar(FileOutputStream("text.dat"))
}

class Sample<T: Closeable, K> {
    fun bar(t: T)
    {
        //...
        t.use {
            //...
        }
    }
}
```

>*Pair sınıfı çok fazla kullanılan basit bir generic sınıftır*

```kotlin
package org.csystem.app

fun main()
{
    val cities = arrayOf(Pair(34, "istanbul"), Pair(6, "ankara"), Pair(35, "izmir"))

    for (p in cities)
        println("${p.first}, ${p.second}")
}
```

>*Pair sınıfı bir data sınıf olduğundan bu sınıfın componentN üye fonksiyonları da bulunmaktadır*

```kotlin
package org.csystem.app

fun main()
{
    val cities = arrayOf(Pair(34, "istanbul"), Pair(6, "ankara"), Pair(35, "izmir"))

    for ((plate, name) in cities)
        println("${plate}, $name")
}
```

>*to generic infix fonksiyonu ile iki değerden bir Pair elde edilebilir*

```kotlin
package org.csystem.app

fun main()
{
    val city = 67 to "zongudak"

    val (plate, name) = city

    println("$plate, $name")

}
```

>*Triple sınıfı*

```kotlin
package org.csystem.app

fun main()
{
    val points = arrayOf(Triple(100, 100, 200), Triple(-100, -100, -200))

    for ((x, y, z) in points)
        println("($x, $y, $z)")
}
```

>*Triple sınıfı (ve Pair sınıfı) immutable özelliktedir*

```kotlin
package org.csystem.app

fun main()
{
    val point = Triple(100, 100, 100)

    point.first = 10 //error
}
```

>*Triple sınıfının (ve Pair sınıfının) toList extension metotları ile elemanlardan oluşan liste elde edilebilir*

```kotlin
package org.csystem.app

fun main()
{
    val point = Triple(100, 100, 100)

    val coordinates = point.toList()

    for (c in coordinates)
        println(c)
}
```

>*Triple sınıfının (ve Pair sınıfının) toList extension metotları ile elemanlardan oluşan liste elde edilebilir*

```kotlin
package org.csystem.app

fun main()
{
    val triple = Triple(67, 372, "zonguldak")

    val list = triple.toList()

    for (c in list)
        print("$c ")

    println()
}
```

```kotlin
/*----------------------------------------------------------------------
	FILE        : Quadruple.kt
	AUTHOR      : Android-Mar-2023 Group
	LAST UPDATE : 31.05.2023

	Quadruple class

	Copyleft (c) 1993 by C and System Programmers Association (org.csystem.app)
	All Rights Free
-----------------------------------------------------------------------*/
package org.csystem.tuple

import java.io.Serializable

data class Quadruple<out T1, out T2, out T3, out T4>(val first: T1, val second: T2, val third: T3, val forth: T4) : Serializable {
    override fun toString() = "($first, $second, $third, $forth)"
}
```

>*Kotlin'de diziler Array isimli bir sınıf ile temsil edilir. Array sınıfı generic bir sınıftır. Aşağıdaki örnekte Array sınıfı kullanılarak bir String dizisi yaratılmıştır. String dizisinin başlangıçta tüm elemanlarına örnekte boş string verilmiştir. Başlangıçta verilen değere ilişkin sentaks ve semantic ileride ele alınacaktır*

```kotlin
package org.csystem.app

import org.csystem.util.string.kotlin.getRandomTextEN
import kotlin.random.Random

fun main()
{
    val a = Array(3) {""}

    for (s in a)
        println(if (s == "") "Boş String" else s)

    println("-------------------------------------")

    for (i in a.indices)
        a[i] = Random.getRandomTextEN(Random.nextInt(5, 10))

    for (s in a)
        println(if (s == "") "Boş String" else s)
}
```

>*Array sınıfı kullanılarak aşağıdaki gibi bir String dizisi oluşturulabilir*

```kotlin
package org.csystem.app

fun main()
{
    val a = Array(3) {"xxx"}

    for (s in a)
        println(s)
}
```

>*Generic fonksiyonlar*

```kotlin
package org.csystem.app

fun main()
{
    val a = "ankara"
    val b = 10

    display<String, Int>(a, b)
}

fun <T, K> display(t: T, k: K)
{
    println(t)
    println(k)
}
```

>*Generic fonksiyonlarda generic parametre türleri tespit edilebiliyorsa açılım yapmaya gerek olmaz*

```kotlin
package org.csystem.app

fun main()
{
    val a = "ankara"
    val b = 10

    display(a, b)
    display(true, 3.4)
}

fun <T, K> display(t: T, k: K)
{
    println(t)
    println(k)
}
```

>*Aşağıdaki örnekte display fonskiyonu çağrısında K türü tespit edilemez*

```kotlin
package org.csystem.app

fun main()
{
    val a = "ankara"
    val b = 10

    display(a) //error
}

fun <T, K> display(t: T) : K?
{
    println(t)

    return null
}
```

>*Yukarıdaki örnekte açılım yapılarak K türü de çağırmada belirtilebilir*

```kotlin
package org.csystem.app

fun main()
{
    val a = "ankara"
    var b = 10

    display<String, Int>(a)
}

fun <T, K> display(t: T) : K?
{
    println(t)

    return null
}
```

>*Aşağıdaki örnekte argümanın türü tespit edebilse bile açılım yapılmak zorundadır. Çünkü K'nın türünün tespit edilmesi için açılım yapılması gerekir*

```kotlin
package org.csystem.app

fun main()
{
    val a = "ankara"
    var b = 10

    display<Int>(a) //error
}

fun <K, T> display(t: T) : K?
{
    println(t)

    return null
}
```

>*Generic fonksiyonların parametrelerine de kısıt (sınır) verilebilir*

```kotlin
package org.csystem.app

import java.io.Closeable
import java.io.FileInputStream

fun main()
{
    doWork<Int, Closeable>(FileInputStream("test.txt"))
    doWork<Int, Double>(3.4) //error
}

fun <R, T: Closeable> doWork(t: T) : R?
{
    t.use {
        //....
    }

    return null
}
```

>*Generic sınıfların içerisinde generic metotlar da bildirilebilir (member generics)*

```kotlin
package org.csystem.app

fun main()
{
    val s = Sample<Int>()

    s.foo("ankara", 10)
    s.foo(2.3, 5)
}

class Sample<T> {
    fun <K> foo(k: K, t: T)
    {
        println(k)
        println(t)
    }
}
```

```kotlin
/*----------------------------------------------------------------------
	FILE        : Value.kt
	AUTHOR      : Android-Mar-2023 Group
	LAST UPDATE : 31.05.2023

	Quadruple class

	Copyleft (c) 1993 by C and System Programmers Association (org.csystem.app)
	All Rights Free
-----------------------------------------------------------------------*/
package org.csystem.tuple

import java.io.Serializable

data class Value<out T>(val value: T) : Serializable {
    fun toList() : List<T> = listOf(value)
}
```


>*Kotlin' de içiçe tür (nested types) bildirimi yapılabilmektedir. İçiçe sınıf bildirimi genel olarak 4(dört) şekilde yapılabilir:*
- Local classes
- Nested classes
- Inner classes
- Anonymous classes

>Kotlin'de yerel sınıflar bildirilebilir. Yerel fonksiyonların varlığından dolayı Kotlin'de yerel sınıfların kullanımına neredeyse gerek olmamaktadır

```kotlin
package org.csystem.app

fun main()
{
    val a = A();

    a.foo(10)
}

class A {
    fun foo(value: Int)
    {
        class B {
            //..."
            fun bar()
            {
                println("bar")
            }
        }

        val b = B()

        b.bar()
    }
}
```

**Nested classes:** 

>*Nested sınıf türünden nesne sınıf dışında kapsayan sınıf ve nokta operatörü ile yaratılabilir*

```kotlin
package org.csystem.app

fun main()
{
    val b = A.B()

    //...
}

class A {
    class B {
        //...
    }

    //...
}

```

>*Nested sınıf içerisinde kapsayan sınıf türünün private elemanlarına erişilebilir*

```kotlin
package org.csystem.app

fun main()
{
    val b = A.B()

    b.foo(20)
}

class A private constructor()  {
    private var x = 10

    class B {
        fun foo(y: Int)
        {
            val a = A()

            println("a.x = ${a.x}")
            println("y = $y")
        }
    }
}
```

>*Kapsayan sınıf nested sınıfın private elemanlarına erişemez*

```kotlin
package org.csystem.app

class A {
    class B private constructor() {
        private var y = 10
        //...
    }

    fun bar()
    {
        val b = B() //error

        b.y = 20 //error
    }
}
```

>*Inner sınıf türünden bir nesne sınıf dışında kapsayan sınıf türünden referans ile yaratılabilir*

```kotlin
package org.csystem.app

fun main()
{
    val a = A()
    val b = a.B()

    b.foo(20)
}

class A {
    private val x = 10

    inner class B {
        fun foo(y: Int)
        {
            println("y=$y")
        }
    }
}

```

>*Inner sınıf içerisinde kapsayan sınıf türünün private elemanlarına erişilebilir. Erişilen private elemanlar inner class nesnesinin yaratılmasında kullanılan kapsayan sınıf nesnesinin elemanlarıdır*

```kotlin
package org.csystem.app

fun main()
{
    val a = A(10)
    val b = a.B()
    val a1 = A(20)
    val b1 = a1.B()

    b.foo(40)
    b1.foo(30)
}

class A (x: Int){
    private var x = x

    inner class B {
        fun foo(y: Int)
        {
            println("x = $x")
            println("y = $y")
        }
    }
}
```

>*Aşağıdaki örnekte kapsayan sınfın foo metodu inner sınıfın foo metodu içerisinde çağrılabilir. İsim aramaya göre bir problem oluşmaz*

```kotlin
package org.csystem.app

fun main()
{
    val b = A().B()

    b.foo(20)
}

class A {
    fun foo()
    {
        println("A.foo")
    }

    inner class B {
        fun foo(y: Int)
        {
            println("B.foo")
            foo()
        }
    }
}
```

>*Aşağıdaki örnekte inner sınıfın içerisinde recursive bir çağrı yapılmıştır*

```kotlin
package org.csystem.app

fun main()
{
    val b = A().B()

    b.foo(20)
}

class A {
    fun foo(x: Int)
    {
        println("A.foo")
    }

    inner class B {
        fun foo(x: Int)
        {
            println("B.foo")
            foo(x) //Dikkat recursive çağrı
        }
    }
}
```

>*Yukarıdaki örnek için "qualified this expression" ile inner sınıfın içerisinde kapsayan sınıfın foo metodu çağrılabilir*

```kotlin
package org.csystem.app

fun main()
{
    val b = A().B()

    b.foo(20)
}

class A {
    fun foo(x: Int)
    {
        println("A.foo")
    }

    inner class B {
        fun foo(x: Int)
        {
            println("B.foo")
            this@A.foo(x)
        }
    }
}
```

>*Anonim sınıflar object anahtar sözcüğü ile bildirilebilir. Anonim sınıf eğer bir interface ile klullanılırsa o interface'i destekleyen, bir sınıf ile kullanılırsa o sınıftan türetilmiş olan bir sınıf bildirimi yapmak aynı zamanda o sınıf türünden bir nesne yaratıp o nesnenin referansını elde etmek anlamındadır. Bildirilen sınıfa isim derleyici tarafından verilir*

```kotlin
package org.csystem.app

fun main()
{
    val ix = object: IX {
        override fun foo()
        {
            println("foo")
        }
    }

    println(ix.javaClass.name)
    ix.foo()
}

interface IX {
    fun foo()
}
```

>*Anonim sınıflar tür belirtilmeden de kullanılabilir*

```kotlin
package org.csystem.app

fun main()
{
    val a = object {
        var x = 10
        var y = 3.4
        //...
    }

    println(a.javaClass.name)
    println(a.x)
    println(a.y)
}
```

>*Anonim sınıflar kendisinden önce bildirilen yerel değişkenleri ve parametre değişkenleri yakalayabilir (capture).* *Aşağıdaki anonim sınıfın derleyici tarafından yazılışının yaklaşık karşılığı (closure):*

```kotlin
class org.csystem.app.AppKt$main$a$1 (private var a: Int) : IX {
            override fun foo()
            {
                println("a = $a")
            }
        }
```

```kotlin
package org.csystem.app

import org.csystem.util.console.kotlin.readInt

fun main()
{
    val a = readInt("Bir sayı giriniz:")

    val ix = object: IX {
        override fun foo()
        {
            println("a = $a")
        }
    }

    ix.foo()
}

interface IX {
    fun foo()
}
```

>*Kotlin'de Java'dan farklı yakalanan bir değişkenin değeri scope'u içerisinde değiştirilebilir*

```kotlin
package org.csystem.app

import org.csystem.util.console.kotlin.readInt

fun main()
{
    var a = readInt("Bir sayı giriniz:")

    val ix = object: IX {
        override fun foo()
        {
            println("a = $a")
            a++
        }
    }

    ix.foo()
    ix.foo()
}

interface IX {
    fun foo()
}
```

>*Kotlin'de Java'dan farklı olarak yakalanan bir değişkenin değeri scope'u içerisinde değiştirilebilir*

```kotlin
package org.csystem.app

import org.csystem.util.console.kotlin.readInt

fun main()
{
    var a = readInt("Bir sayı giriniz:")

    val ix = object: IX {
        override fun foo()
        {
            println("a = $a")
        }
    }
    ++a
    ix.foo()
    ix.foo()

}

interface IX {
    fun foo()
}
```

>*object bildirimi (object declaration). object olarak bildirilmiş olan türe ilişkin bir tane nesne yaratılmış olur ve ismi o nesnenin referansı olarak kullanılır. Kullanım şekli itibariyla static elemanları varmış gibi erişilir*

```kotlin
package org.csystem.app

fun main()
{
    Singleton.foo(23)
    Singleton.bar(10.0)
}

object Singleton {
    fun foo(a: Int)
    {
        println("foo")
    }

    fun bar(b: Double)
    {
        println("bar")
    }
}
```

>*object bildirimi (object declaration). object olarak bildirilmiş olan türe ilişkin bir tane nesne yaratılmış olur ve ismi o nesnenin referansı olarak kullanılır. Kullanım şekli itibariyla static elemanları varmış gibi erişilir*

```kotlin
package org.csystem.app

fun main()
{
    Singleton.foo(23)
    Singleton.bar()
}

object Singleton {
    private var mX : Int = 0

    fun foo(x: Int)
    {
        mX = x
    }

    fun bar()
    {
        println("mX = $mX")
    }
}
```

>*object bildirimleri başka bir sınıftan türetilebilir*

```kotlin
package org.csystem.app

fun main()
{
    Singleton(23)
}

open class A

object Singleton : A() {
    operator fun invoke(b: Int)
    {
        println("b = $b")
    }

    fun foo(a: Int)
    {
        println("foo")
    }

    fun bar(b: Double)
    {
        println("bar")
    }
}
```

>*object bildirimleri*

```kotlin
package org.csystem.app

fun main()
{
    val s = Singleton

    s(23)
    Singleton(23)
}

open class A

object Singleton : A() {
    operator fun invoke(b: Int)
    {
        println("b = $b")
    }

    fun foo(a: Int)
    {
        println("foo")
    }

    fun bar(b: Double)
    {
        println("bar")
    }
}

```

>*Aşağıdaki örnekte nesne yaratılmamıştır. Fonksiyon çağırma operatör fonksiyonu çağrılmıştır. Dikkat edilirse s'nin türü Unit'dir*

```kotlin
package org.csystem.app

fun main()
{
    val s: Unit = Sample(10)

    println(s.javaClass.name)
}

open class A

object Sample : A() {
    operator fun invoke(b: Int)
    {
        println("b = $b")
    }

    fun foo(a: Int)
    {
        println("foo")
    }

    fun bar(b: Double)
    {
        println("bar")
    }
}
```

>*object bildirimleri (singleton) türetmeye kapalıdır*

```kotlin
package org.csystem.app

object A
object Singleton : A { //error
    operator fun invoke(b: Int)
    {
        println("b=$b")
    }
    fun foo(a: Int)
    {
        println("foo")
    }

    fun bar(b: Double)
    {
        println("bar")
    }
}
```

>*object bildirimleri bir sınıf içerisinde yapılabilir*

```kotlin
package org.csystem.app

fun main()
{
    Sample.Mample.foo()
}

class Sample {
    object Mample {
        fun foo()
        {
            println("foo")
        }
    }
}
```

>*Aşağıdaki örnekte `**` ile belirtilen ifade için invoke metodu çağrılır*

```kotlin
package org.csystem.app

fun main()
{
    val A = A(10)
    val x = A(20) //**

    println(x)
}


class A(x: Int) {
    init {
        println("A.ctor, int")
    }

    operator fun invoke(x: Int) : Int
    {
        println("A.invoke")

        return x
    }
}
```