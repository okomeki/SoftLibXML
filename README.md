# SoftLibXML
XML API Wrap and RSS/Atom Reader API

# about

XML の読み込み用の簡単なAPIです。一覧をListやMapで扱えるよう軽くラップしています。
RSS/Atom Reader も追加してみました。

# 使い方

Maven 系
module非対応版(JDK8)
~~~
<dependency>
  <groupId>net.siissie</groupId>
  <artifactId>softlib-xml</artifactId>
  <version>1.0.2</version>
</dependency>
~~~
module対応版 (JDK11)
~~~
<dependency>
  <groupId>net.siissie</groupId>
  <artifactId>softlib-xml.module</artifactId>
  <version>1.0.2</version>
</dependency>
~~~
を dependencies に加えます。
次版は1.0.3-SNAPSHOT