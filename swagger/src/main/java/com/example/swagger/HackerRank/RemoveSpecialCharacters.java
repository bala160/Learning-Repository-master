package com.example.swagger.HackerRank;

public class RemoveSpecialCharacters {
    public static void main(String[] args) {
        String name = "nbibqfgtpqabtvcprapnhvwmummqxpteitvabduimmjhblokdgxcdvopbsibbdvkwtkpowxccabedqogwgbrthavcioddlncwqjnobpjbjnfbtdpreanmakgbabedhwcxipfjwochthahlhqhhflahjbxlfdsefjemgncnpcapchscarjvfbfoceraxluumaitflimfithinjahlneeskhpexohtlullhiopmtpitpnepmhfqmptlgxknwvakadrisamniuixkfhwemhqtctsrvhpkxckclkmmxsulctvxsmeglmsnimwvtfisxsmlvbpmtlxnwnfrbbpmnjrnnhlimlsmfxpsqggljwqxlnieoaiebbjpimpuxcavbpprvouwmewpsgtiwdeaeohesoqsqjhrawkoewmidkxvjsfhvkxrqgnkngufhdxhsbnwashdegqfbvnoxmiplnsqtovcsucmohcgbbjwirdboqhovhvavpqigfdqsfuimwooqpeaihroqaefasvvjnfhkixeflmkcsqsjukjddaltuqtoniqsopfohbmsowuaooccalvtdjharsgxdojjggpnidhobuohkqbduxwxavprqoqtecukilxipxwitnivvjqripjieacuokhledvnpnvwemgxbomoqfaamjbqbdnifmtkqipxvevbqtqjcfascasobtwcodkmqvwigfxtjuvshfdjcdtetgkurjpimrmepcenhdiqpvkxdgavbusvcertfatssxojblwiowrwudvpbnmdbgatdjhikcsecbfdmtecrvanqnustbvuxnqcoxdarnulobhcmlvgivlqdikwbxkaecttrotddiosbnuoaruahxjcxtaalcbpvmkocwrvwapxnengnbwmrhoqtgrgjkngwamqownmwvddbjbgkatdxjmsqfqrpgxfwdnmbsqpptliemphmccmlowtlpmsvdplhexaphqwsdhphowmqqqtxopcftvsxmfwjuoatg{-truncated-}";
        /*String fin = "";

        for (int i = 0; i < name.length(); i++) {
            if (name.charAt(i) > 64 && name.charAt(i) <= 122) {
                fin += name.charAt(i);
            }
        }
        System.out.println(fin);*/
        String arr = name.replaceAll("[^a-zA-Z0-9]+", " ");
        String[] fin = arr.split(" ");
        System.out.println(fin.length);

        for(String res : fin){
            System.out.println(res);
        }

        }
    }

