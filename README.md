# metis

"Philosophisch steht Metis für den Scharfsinn, der als „praktisches, komplexes, implizites Wissen von den drei anderen
Wissensformen episteme, techne und phronesis unterschieden wird." - Wikipedia.

Weiter heißt es "Bei Michael Köhlmeier ist eine Variante nachzulesen, in der Metis sich in alle möglichen pflanzlichen
und tierischen Formen verwandelte, um dem ihr nachstellenden Zeus zu entkommen." - Wikipedia.

In diesem Repository möchte ich demonstrieren, wie Anwendungen verschiedene Werkzeuge aus dem Domain-Driven-Design
verbunden mit einer hexagonalen Architektur nutzen können, um ihre komplexität zu adressieren und die Anwendung wartbar
zu halten. Da sich die Struktur meiner Applikation in diesem Repository vermutlich stetig weiterentwickeln wird und sich
in alle möglichen Formen verwandeln kann, habe ich mich für den Arbeitstitel <i>metis</i> entschieden.

Als Fachlichkeit für unsere Applikation bediene ich mich an Anwendungsfällen für ein ERP-System (Enterprise Resource
Planning) innerhalb eines Beraterunternehmens. Diese entstammen jedoch lediglich meiner Auffassung.

An dieser Stelle ein kleiner Disclaimer:

<i>Die Struktur meiner Anwendung ist höchst subjektiv und meine Interpretation einer möglichen Lösung für eine
hexagonale Architektur. Meine Art und Weise eine solche Applikation zu strukturieren ändert sich hin und wieder,
weil ich immer wieder neue Erkenntnisse habe, andere Menschen gute Ideen haben und ich neue Dinge probiere, die
Auswirkung auf die Struktur haben.

Wichtig ist, dass unabhängig von der Struktur, die Ziele, die eine hexagonale Architektur verfolgen sollte, nicht aus
den Augen verloren werden.
</i>

# Module / bounded-context

Was genau also nun tun, um besagte Komplexität zu adressieren?

Metis ist dazu in verschiedene Module unterteilt. Diese orientieren sich streng an einem zuvor
erarbeitetem [bounded-context](https://martinfowler.com/bliki/BoundedContext.html) - ein Ergebnis des strategischen
Designs. Die Folge ist, dass wir anstatt einem schweren und zentralen Domänenmodell hoffentlich mehreren überschaubaren
Domänenmodellen gegenüberstehen. Auf die Mittel und Wege um diese kontextuellen Grenzen zu erarbeiten, möchte ich an
dieser Stelle nicht weiter eingehen - Stichwort ist strategisches Design (DDD).

Jedes der Module (bounded-context), die Metis bereitstellt, ist unterteilt in verschiedene Bereiche oder Schichten einer
hexagonalen Architektur. Die Namen dieser einzelnen Bereiche können im Internet variieren. Ich persönlich entscheide
mich in diesem Projekt für "domain", "application" und "adapters". Es kann durchaus auch mehr als drei Bereiche geben.
Ein Beispiel dafür, welches mir bereits begegnet ist, wäre: "domain", "application", "ui", "infrastructure".

# Domain

Der Bereich der Domain enthält ausschließlich fachlichen Code. In meinem Fall die Ergebnisse des taktischen Designs.  
Im taktischen Design gehts entgegen dem strategischen Design um unseren Code - unsere Packages und Klassen.
Es gibt verschiedene Bausteine, die wir uns zu nutzen machen werden, um unser Domänenmodell möglichst reich an
Fachlichkeit und unabhängig von der Außenwelt zu gestalten.

Application-Frameworks, wie bspw. Spring, Micronaut oder Quarkus haben in diesem Bereich noch nichts verloren.
Trotzdem setzen wir bestimmte Bibliotheken ein, die uns bei der Erledigung der Fachlichkeit und bei der Analyse des
Gesundheitszustands unserer Applikation unterstützen.
Beispiele dafür könnten sein: "apache-commons", "logging", "lombok", ....

In meinem Fall kommen in den Modulen auch noch verschiedene Aspekte
von [jmolecules](https://github.com/xmolecules/jmolecules) zum Einsatz um meine Bausteine zu "klassifizieren".

## Aggregates

Der erste wichtige Baustein aus dem taktischen Design, den ich mir in diesem Projekt zu nutzen machen möchte,
ist das [Aggregat](https://martinfowler.com/bliki/DDD_Aggregate.html). Innerhalb meiner Aggregate entstehen anschließend
mindestens eine vermutlich mehrere Entitäten. Das Erarbeiten eines Aggregats kann durchaus ein längerer Prozess sein,
oder erst während der Arbeit geschehen.

Sprich: mein Modell wird nicht von Beginn an vollständig sein und sich mit Sicherheit mit der Zeit verändern.

## Value Objects

<i>"A small simple object, like money or a date range, whose equality isn't based on identity"</i> - Martin Fowler

Entgegen einer Entität, die eine eigenständige Identität hat, wird bei Value Objects oder Wertobjekten nur der Inhalt
herangezogen um diese miteinander zu vergleichen.

In diesem Projekt nutze ich Value Objects, um den Wert und die Validität eines Attributes in unserer Domäne
zusammenzubringen.

Ein Beispiel: Mitarbeiter in meiner Applikation besitzen einen Vornamen. Dieser wird üblicherweise als String
abgebildet.

Es würde also eine Klasse <i>Mitarbeiter</i> entstehen, die das Attribut <i>vorname</i> vom Typ <i>String</i>
beinhaltet.
Diese Herangehensweise hat aus meiner Sicht den wichtigen Nachteil, dass die Verantwortlichkeit darüber, zu entscheiden,
wann ein Vorname valide ist durch jemand anderen, ggf. die Mitarbeiterklasse oder eine Validator-Utility übernommen
wird.

Eine Möglichkeit diesen Nachteil anzugehen, wäre eine Klasse <i>Vorname</i> einzuführen, die nicht nur den Wert, sondern
auch die Geschäftsregeln im Bezug auf einen Vornamen beinhaltet und auch durchsetzt.

# Application

# Adapters

