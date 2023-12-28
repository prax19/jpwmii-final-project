# Projekt zaliczeniowy JPWMII
Projekt końcowy z przedmiotu JPWMII (IV rok informatyki, Uniwersytet Śląski).
Założeniem tego projektu jest stworzenie protej gry komputerowej w języku Java, korzystającej z podstawowych technik i narzędzi do generowania obrazu. Niniejszy projekt to gra 2D typu space shooter, prezentująca przykład wykorzystania biblioteki JavaFX.

## Wymagania
Przekreślony tekst reprezentuje wykonane elementu zadania. Po spełnieniu wszystkich wymagań zadania, usunąć przekreślenia. Po ukończeniu projektu, usunąć niniejszy tekst.

1. **Implementacja podstawowa**
- [x] Komponent multimedialny korzystający z zasobów - grafika,dźwięk (zasoby umieszczone w pliku jar).
- [x] Uruchamianie aplikacji z pliku JAR (pliku uruchomieniowego)
2. **Generowanie i przekształcanie obrazu**
- [x] Kopiowanie fragmentów obrazu, tło (animacje w tle), elementy gry (np. elementy stałe, przeszkody, postaci) - wycinanie i wklejanie fragmentów obrazów.
- [x] Metody rysowania grafiki np. teksty, linie, kształty geometryczne, wypełnianie obszarów
3. **Animacje**
- [x] Animacja z użyciem Timera Swing. Zmiana położenia, skali, orientacji obiektów graficznych
- [x] Zmiana klatek animacji - animacja postaci, elementów gry, elementów tła
4. **Interakcja z użytkownikiem**
- [x] Interakcja z użytkownikiem (mysz, klawiatura)  
- [ ] Dźwięki tła i zdarzeń  (zasoby w pliku uruchomieniowym - jeżeli technologia pozwala).
5. **Logika**
- [ ] ~~Interakcja elementów sceny, kolizje, sterowanie elementami gry, proste AI,~~ generowanie poziomów,
generowanie obiektów na scenie.

## Współautorzy
- Filip Mędrala - tekstura statku, testowanie, konsultacje

## TODO
- ### Animacje elementów gry
  - Animacja wystrzału pocisku
  - Animacja zniszczania pocisku
  - Animacja kolizji statku
- ### Dźwięki
  - Dźwięk wystrzału
  - Dźwięk trafienia / kolizji
  - Dźwięk silnika
  - Ambient w tle
-  ### Obiekt przeszkoda
  - przeszkoda w postaci meteorytu na pierwszym planie
    - zadaje obrażenia
    - można go trafić
    - zmienia pozycję oraz zwrot
- ### Losowe generowanie obiektów na mapie
  - generowanie meteorytów
  - generowanie przeciwników
    - max 3 przeciwników na mapie
- ### Punktowanie i dodatkowe mechaniki
  - przyznawane punkty za zniszczenie przeciwników
    - bonus za zniszczenie przeciwnika w obecności innego
  - leczenie 10HP co 5 sekund dla wszystkich statków
  - gracz po zniszczeniu swojego statku widzi wynik punktowy
    - przycisk `F` powoduje reset gry