# metis

"Philosophisch steht Metis für den Scharfsinn, der als „praktisches, komplexes, implizites Wissen [...]" - Wikipedia.

In diesem Repository möchte ich demonstrieren, wie Anwendungen verschiedene Werkzeuge aus dem Domain-Driven-Design
verbunden mit einer hexagonalen Architektur nutzen können, um ihre komplexität zu addressieren und die Anwendung wartbar
zu halten.

Als Beispiel habe ich mich für Resourcen-Management innerhalb eines Beraterunternehmens entschieden.

An dieser Stelle ein kleiner Disclaimer

<i>Die Struktur meiner Anwendung ist höchst subjektiv und meine Interpretation einer möglichen Lösung für eine
hexagonale Architektur. Selbst meine Art und Weise eine solche Applikation zu strukturieren ändert sich hin und wieder,
weil
ich immer wieder neue Erkenntnisse habe und neue Dinge probiere, die Auswirkung auf die Struktur haben.

Wichtig ist, dass unabhängig von der Struktur, die Ziele, die eine hexagonale Architektur erreichen sollte, nicht aus
den Augen verloren werden.
</i>

# Module / bounded-context

Metis ist dazu in verschiedene Module unterteilt. Diese orientieren sich streng nach einem zuvor
erarbeitetem [bounded-context](https://martinfowler.com/bliki/BoundedContext.html) - ein Ergebnis des strategischen
Designs. Anstatt eines schweren und zentralen Domänenmodells sollten in diesem Prozess mehrere, greifbarere
Domänenmodelle entstehen.

Jedes dieser Module ist unterteilt in verschiedene Bereiche oder Schichten einer hexagonalen Architektur.
Die Namen dieser einzelnen Bereiche können im Internet variieren. Ich persönlich entscheide mich in diesem Projekt für
"domain", "application" und "adapters". Es kann durchaus auch mehr als drei Bereiche geben.
Beispiel: "domain", "application", "ui", "infrastructure".

# Domain

Der Bereich der Domain enthält ausschließlich fachlichen Code. In meinem Fall die Ergebnisse des taktischen Designs.  
Im taktischen Design gehts entgegen dem strategischen Design um unseren Code - unsere Packages und Klassen.
Es gibt verschiedene Bausteine, die wir uns zu nutzen machen werden, um unser Domänenmodell möglichst reich an
Fachlichkeit und unabhängig von der Außenwelt zu gestalten.

# Application

# Adapters

