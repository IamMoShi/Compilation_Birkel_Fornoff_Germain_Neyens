with Ada.Text_IO; use Ada.Text_IO;

procedure Facto is
    function Calcule_Factorielle(n : Integer) return Integer is
    begin
        if n <= 1 then
            return 1;
        else
            return n * Calcule_Factorielle(n - 1);
        end if;
    end Calcule_Factorielle;

    N : Integer;
    Resultat : Integer;
begin
    -- Lire la valeur de N
    Put_Line("Entrez un nombre pour calculer sa factorielle : ");
    Get(N);

    -- Calcul de la factorielle
    Resultat := Calcule_Factorielle(N);

    -- Afficher le résultat
    Put_Line("La factorielle de " & Integer'Image(N) & " est " & Integer'Image(Resultat));
end Facto;
