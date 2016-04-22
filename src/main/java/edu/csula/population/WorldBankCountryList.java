package edu.csula.population;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Created by Access on 4/20/2016.
 */
public class WorldBankCountryList {

    final static String[] countries = new String[]{ 
            
            "AW",

    "AF",

    "AO",

    "AL",

    "AD",

    "AE",

    "AR",

    "AM",

    "AS",

    "AG",

    "AU",

    "AT",

    "AZ",

    "BI",

    "BE",

    "BJ",

    "BF",

    "BD",

    "BG",

    "BH",

    "BS",

    "BA",

    "BY",

    "BZ",

    "BM",

    "BO",

    "BR",

    "BB",

    "BN",

    "BT",

    "BW",

    "CF",

    "CA",

    "CH",

    "JG",

    "CL",

    "CN",

    "CI",

    "CM",

    "CD",

    "CG",

    "CO",

    "KM",

    "CV",

    "CR",

    "CU",

    "CW",

    "KY",

    "CY",

    "CZ",

    "DE",

    "DJ",

    "DM",

    "DK",

    "DO",

    "DZ",

    "EC",

    "EG",

    "ER",

    "ES",

    "EE",

    "ET",

    "FI",

    "FJ",

    "FR",

    "FO",

    "FM",

    "GA",

    "GB",

    "GE",

    "GH",

    "GN",

    "GM",

    "GW",

    "GQ",

    "GR",

    "GD",

    "GL",

    "GT",

    "GU",

    "GY",

    "HK",

    "HN",

    "HR",

    "HT",

    "HU",

    "ID",

    "IM",

    "IN",

    "IE",

    "IR",

    "IQ",

    "IS",

    "IL",

    "IT",

    "JM",

    "JO",

    "JP",

    "KZ",

    "KE",

    "KG",

    "KH",

    "KI",

    "KN",

    "KR",

    "XK",

    "KW",

    "LA",

    "LB",

    "LR",

    "LY",

    "LC",

    "LI",

    "LK",

    "LS",

    "LT",

    "LU",

    "LV",

    "MO",

    "MF",

    "MA",

    "MC",

    "MD",

    "MG",

    "MV",

    "MX",

    "MH",

    "MK",

    "ML",

    "MT",

    "MM",

    "ME",

    "MN",

    "MP",

    "MZ",

    "MR",

    "MU",

    "MW",

    "MY",

    "NA",

    "NC",

    "NE",

    "NG",

    "NI",

    "NL",

    "NO",

    "NP",

    "NZ",

    "OM",

    "PK",

    "PA",

    "PE",

    "PH",

    "PW",

    "PG",

    "PL",

    "PR",

    "KP",

    "PT",

    "PY",

    "PS",

    "PF",

    "QA",

    "RO",

    "RU",

    "RW",

    "SA",

    "SD",

    "SN",

    "SG",

    "SB",

    "SL",

    "SV",

    "SM",

    "SO",

    "RS",

    "SS",

    "ST",

    "SR",

    "SK",

    "SI",

    "SE",

    "SZ",

    "SX",

    "SC",

    "SY",

    "TC",

    "TD",

    "TG",

    "TH",

    "TJ",

    "TM",

    "TL",

    "TO",

    "TT",

    "TN",

    "TR",

    "TV",

    "TZ",

    "UG",

    "UA",

    "UY",

    "US",

    "UZ",

    "VC",

    "VE",

    "VI",

    "VN",

    "VU",

    "WS",

    "YE",

    "ZA",

    "ZM",

    "ZW"
//
//
//            "AF",
//            "AX",
//            "AL",
//            "DZ",
//            "AS",
//            "AD",
//            "AO",
//            "AI",
//            "AQ",
//            "AG",
//            "AR",
//            "AM",
//            "AW",
//            "AU",
//            "AT",
//            "AZ",
//            "BS",
//            "BH",
//            "BD",
//            "BB",
//            "BY",
//            "BE",
//            "BZ",
//            "BJ",
//            "BM",
//            "BT",
//            "BO",
//            "BA",
//            "BW",
//            "BV",
//            "BR",
//            "IO",
//            "BN",
//            "BG",
//            "BF",
//            "BI",
//            "KH",
//            "CM",
//            "CA",
//            "CV",
//            "KY",
//            "CF",
//            "TD",
//            "CL",
//            "CN",
//            "CX",
//            "CC",
//            "CO",
//            "KM",
//            "CG",
//            "CD",
//            "CK",
//            "CR",
//            "CI",
//            "HR",
//            "CU",
//            "CY",
//            "CZ",
//            "DK",
//            "DJ",
//            "DM",
//            "DO",
//            "EC",
//            "EG",
//            "SV",
//            "GQ",
//            "ER",
//            "EE",
//            "ET",
//            "FK",
//            "FO",
//            "FJ",
//            "FI",
//            "FR",
//            "GF",
//            "PF",
//            "TF",
//            "GA",
//            "GM",
//            "GE",
//            "DE",
//            "GH",
//            "GI",
//            "GR",
//            "GL",
//            "GD",
//            "GP",
//            "GU",
//            "GT",
//            "GG",
//            "GN",
//            "GW",
//            "GY",
//            "HT",
//            "HM",
//            "VA",
//            "HN",
//            "HK",
//            "HU",
//            "IS",
//            "IN",
//            "ID",
//            "IR",
//            "IQ",
//            "IE",
//            "IM",
//            "IL",
//            "IT",
//            "JM",
//            "JP",
//            "JE",
//            "JO",
//            "KZ",
//            "KE",
//            "KI",
//            "KR",
//            "KW",
//            "KG",
//            "LA",
//            "LV",
//            "LB",
//            "LS",
//            "LR",
//            "LY",
//            "LI",
//            "LT",
//            "LU",
//            "MO",
//            "MK",
//            "MG",
//            "MW",
//            "MY",
//            "MV",
//            "ML",
//            "MT",
//            "MH",
//            "MQ",
//            "MR",
//            "MU",
//            "YT",
//            "MX",
//            "FM",
//            "MD",
//            "MC",
//            "MN",
//            "ME",
//            "MS",
//            "MA",
//            "MZ",
//            "MM",
//            "NA",
//            "NR",
//            "NP",
//            "NL",
//            "AN",
//            "NC",
//            "NZ",
//            "NI",
//            "NE",
//            "NG",
//            "NU",
//            "NF",
//            "MP",
//            "NO",
//            "OM",
//            "PK",
//            "PW",
//            "PS",
//            "PA",
//            "PG",
//            "PY",
//            "PE",
//            "PH",
//            "PN",
//            "PL",
//            "PT",
//            "PR",
//            "QA",
//            "RE",
//            "RO",
//            "RU",
//            "RW",
//            "BL",
//            "SH",
//            "KN",
//            "LC",
//            "MF",
//            "PM",
//            "VC",
//            "WS",
//            "SM",
//            "ST",
//            "SA",
//            "SN",
//            "RS",
//            "SC",
//            "SL",
//            "SG",
//            "SK",
//            "SI",
//            "SB",
//            "SO",
//            "ZA",
//            "GS",
//            "ES",
//            "LK",
//            "SD",
//            "SR",
//            "SJ",
//            "SZ",
//            "SE",
//            "CH",
//            "SY",
//            "TW",
//            "TJ",
//            "TZ",
//            "TH",
//            "TL",
//            "TG",
//            "TK",
//            "TO",
//            "TT",
//            "TN",
//            "TR",
//            "TM",
//            "TC",
//            "TV",
//            "UG",
//            "UA",
//            "AE",
//            "GB",
//            "US",
//            "UM",
//            "UY",
//            "UZ",
//            "VU",
//            "VE",
//            "VN",
//            "VG",
//            "VI",
//            "WF",
//            "EH",
//            "YE",
//            "ZM",
//            "ZW"
    };

    public static LinkedList<String> getCountryListcode() {
        return new LinkedList<>(Arrays.asList(countries));
    }
}
