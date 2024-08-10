
### C ve Sistem Programcıları Derneği
### Android Programlama Kursu
### Kotlin Programlama Dili
### Eğitmen: Oğuz KARAN

**_Anahtar Notlar:_** Burada ağırlıklı olarak KotlinJVM üzerinde durulacaktır.

>*Hello, World programı. Kotlin 1.3 versiyonundan itibaren main fonksiyonunun parametresi olmak zorunda değildir*

```
package org.csystem.app

fun main() {
    println("Hello, World")
}
```
**_Anahtar Notlar:_** Kotlin'de bir fonksiyon fun anahtar sözcüğü ile bildirilir. Kotlin'de bir fonksiyonun geri dönüş 
değeri fonksiyon isminden önce yazılmaz. Bir fonksiyonun geri dönüş değeri yoksa herhangi bir geri dönüş değeri bilgisi 
yazılmayabilir.

>*Bir fonksiyonu çağıran fonksiyon (caller) ile çağrılan fonksiyon (callee) aynı pakette ise paket ismi kullanılmayabilir.
Yani aynı ".kt" uzantılı dosyada bulunan veya farklı dosyada fakat aynı paket altında bulunan fonksiyonlar doğrudan 
çağrılabilir*

```
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

>*Bir fonksiyonun geri dönüş değeri fonksiyon bildiriminde gövde yazılmadan önce, :'den sonra yazılmalıdır. Unit
C, C++, Java ve C#'daki void anahtar sözcüğüne karşılık getirilebilir. Kotlin 1.1 versiyonundan sonra geri dönüş
değeri olmayan fonksiyonlar için Unit yazılması zorunlu değildir*

```
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


**Anahtar Notlar:** Kotlin'de temel türler (primitive/built-in/pre-defined types) sınıf ile temsil edilmiştir <br>
**Anahtar Notlar:** Kotlin'de "işaretsiz (unsigned)" tamsayı türleri de bulunur. Bunlar ileride ele alınacaktır

>* *

```

```

>* *

```

```

>* *

```

```

>* *

```

```

>* *

```

```
