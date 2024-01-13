with Ada.Text_IO; use Ada.Text_IO;

procedure Serie_Bertrand is
    function Calcule_Bertrand(n : Integer) return Integer is
        Resultat : Integer := 1;
    begin
        for i in 1..n loop
            Resultat := Resultat * 2;
        end loop;
        return Resultat;
    end Calcule_Bertrand;

    N : Integer;
    Resultat : Integer;
begin
    -- Lire la valeur de N
    Put_Line("Entrez un nombre pour générer la série de Bertrand : ");
    Get(N);

    -- Génération de la série de Bertrand
    Resultat := Calcule_Bertrand(N);

    -- Afficher le résultat
    Put_Line("Le " & Integer'Image(N) & "ème terme de la série de Bertrand est " & Integer'Image(Resultat));
end Serie_Bertrand;
