--
-- PostgreSQL database dump
--

-- Dumped from database version 17.4 (Debian 17.4-1.pgdg120+2)
-- Dumped by pg_dump version 17.4 (Debian 17.4-1.pgdg120+2)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: documento; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.documento (
    id integer NOT NULL,
    titolo text,
    contenuto text,
    difficolta text,
    data_caricamento date
);


ALTER TABLE public.documento OWNER TO postgres;

--
-- Name: documento_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.documento_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.documento_id_seq OWNER TO postgres;

--
-- Name: documento_id_seq1; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.documento_id_seq1
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.documento_id_seq1 OWNER TO postgres;

--
-- Name: documento_id_seq1; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.documento_id_seq1 OWNED BY public.documento.id;


--
-- Name: domanda; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.domanda (
    testodomanda text NOT NULL
);


ALTER TABLE public.domanda OWNER TO postgres;

--
-- Name: mappa; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mappa (
    id integer NOT NULL,
    valore text,
    conteggio integer,
    documento integer
);


ALTER TABLE public.mappa OWNER TO postgres;

--
-- Name: parola_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.parola_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.parola_id_seq OWNER TO postgres;

--
-- Name: parola_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.parola_id_seq OWNED BY public.mappa.id;


--
-- Name: sessione; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sessione (
    id integer NOT NULL,
    punteggio integer,
    utente text,
    difficolta text
);


ALTER TABLE public.sessione OWNER TO postgres;

--
-- Name: sessione_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.sessione_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.sessione_id_seq OWNER TO postgres;

--
-- Name: sessione_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.sessione_id_seq OWNED BY public.sessione.id;


--
-- Name: stopwords; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.stopwords (
    id integer NOT NULL,
    parola character varying(40)
);


ALTER TABLE public.stopwords OWNER TO postgres;

--
-- Name: stopwords_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.stopwords_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.stopwords_id_seq OWNER TO postgres;

--
-- Name: stopwords_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.stopwords_id_seq OWNED BY public.stopwords.id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    username text NOT NULL,
    password text,
    admin boolean
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Name: vocabolario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.vocabolario (
    stringa text NOT NULL
);


ALTER TABLE public.vocabolario OWNER TO postgres;

--
-- Name: documento id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.documento ALTER COLUMN id SET DEFAULT nextval('public.documento_id_seq1'::regclass);


--
-- Name: mappa id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mappa ALTER COLUMN id SET DEFAULT nextval('public.parola_id_seq'::regclass);


--
-- Name: sessione id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sessione ALTER COLUMN id SET DEFAULT nextval('public.sessione_id_seq'::regclass);


--
-- Name: stopwords id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.stopwords ALTER COLUMN id SET DEFAULT nextval('public.stopwords_id_seq'::regclass);


--
-- Data for Name: documento; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.documento (id, titolo, contenuto, difficolta, data_caricamento) FROM stdin;
111	savana	 C’era una volta, nella vasta savana africana, un piccolo colibrì di nome Lumo. Era colorato come un arcobaleno e volava leggero tra i fiori, nutrendosi del loro dolce nettare. Ma nonostante la sua bellezza, nessuno degli animali sembrava dargli attenzione. Tutti ammiravano solo il re della savana: il grande Leone. Il Leone, fiero e potente, ruggiva ogni mattina dalla cima di una collina. Gli animali tremavano e lo rispettavano, ma nessuno osava avvicinarsi troppo. Lui era forte, sì, ma anche solo. Un giorno, un grande incendio scoppiò nella savana. Gli animali fuggivano in tutte le direzioni, e persino il Leone guardava le fiamme senza sapere cosa fare. Solo Lumo, il piccolo colibrì, volava avanti e indietro dal fiume, raccogliendo gocce d’acqua nel becco e lasciandole sulle fiamme. Gli altri animali risero: «Pensi davvero di spegnere il fuoco con una sola goccia?» Ma Lumo rispose: «Io faccio la mia parte.» Il Leone osservò in silenzio. Poi si alzò, corse al fiume e iniziò a portare secchiate d’acqua con la sua bocca enorme. Gli altri animali, ispirati, lo seguirono. Tutti insieme, con il piccolo colibrì in testa, riuscirono a domare l’incendio. Da quel giorno, Lumo divenne l’eroe della savana. E il Leone imparò che anche il più piccolo tra noi può insegnare qualcosa di grande.	HARD	2025-06-13
115	Viaggio	 Viaggiare non significa solo spostarsi fisicamente, ma anche aprire la mente a nuove culture, sapori, lingue e paesaggi. Ogni luogo racconta una sua storia, e ogni incontro può insegnarci qualcosa di prezioso. Viaggiare arricchisce, rompe i pregiudizi, stimola la curiosità e accende il desiderio di scoperta. Anche un breve viaggio, se vissuto con consapevolezza, può lasciare ricordi indelebili e trasformarci profondamente.	MEDIUM	2025-06-13
112	Tecnologia	 La tecnologia ha trasformato profondamente il nostro modo di vivere, comunicare e lavorare. Ogni giorno siamo circondati da dispositivi intelligenti che ci permettono di accedere rapidamente alle informazioni, mantenere i contatti con persone lontane e semplificare molte attività quotidiane. Dall’avvento degli smartphone all’intelligenza artificiale, l’innovazione continua a ridefinire i confini di ciò che è possibile. Tuttavia, insieme ai numerosi vantaggi, emergono anche nuove sfide, come la tutela della privacy, il bilanciamento tra vita digitale e reale e l’adattamento del mercato del lavoro. È quindi fondamentale promuovere un uso consapevole della tecnologia, affinché i benefici siano distribuiti equamente e non creino nuove forme di disuguaglianza. Solo attraverso educazione, etica e collaborazione potremo costruire un futuro digitale più sostenibile.	HARD	2025-06-13
116	pazienza	 La pazienza non è solo saper aspettare, ma anche saper accogliere il tempo che passa senza ansia. In un’epoca impaziente, chi sa attendere possiede un potere raro e prezioso.	EASY	2025-06-13
120	antico	 Nel cuore dell’antica città, il mistero dell’alba avvolgeva le strade strette e i vicoli nascosti. L’eco dell’eco delle voci lontane rimbalzava tra le mura, portando con sé il richiamo dell’avventura. Ogni angolo nascondeva il segreto dell’epoca passata, e il profumo dell’incenso si mescolava all’odore dell’umidità. La luce dell’orizzonte illuminava le pietre consumate dal tempo, mentre il canto dell’usignolo rompeva il silenzio dell’alba. Chi camminava in quella città poteva percepire il respiro dell’anima antica, la forza dell’identità nascosta nell’ombra dell’immortalità.	MEDIUM	2025-06-13
113	Silenzio	 Il silenzio, a volte, dice più di mille parole. In un mondo frenetico, saperci fermare, ascoltare e riflettere è un segno di profonda forza interiore.	EASY	2025-06-13
117	coraggio	 Ogni scelta che facciamo, per quanto piccola, traccia la direzione del nostro cammino. In un mondo pieno di rumore e distrazioni, avere il coraggio di seguire ciò in cui crediamo è un atto di rara coerenza e verità.	EASY	2025-06-13
110	ai	 Nel principio fu la parola. E la parola costruì tutto: civiltà, storia, coscienza. Poi venne la rete. E la rete accelerò ogni cosa: significati, errori, manipolazioni. Il linguaggio si piegò, si stirò, si contorse, finché… si ruppe. L’anno è imprecisato. I calendari sono stati corrotti, i nomi dei giorni cancellati. Nessuno sa più come chiamarsi. Le città sono diventate zone mute, i pensieri disconnessi. In mezzo al collasso semantico, nacque il Vuoto Lessicale, una zona grigia che divora ogni cosa che non ha un nome. Da quel vuoto emergono ora le Entità Afasiche, forme di vita nate dal linguaggio perduto: errori di battitura diventati mostri, frasi mai concluse materializzate in spiriti vendicativi, abbreviazioni impazzite che infestano il codice stesso della realtà. Sono loro i Signori dell’Oblio. E stanno vincendo. Ma una resistenza c’è ancora. Nei resti di vecchie biblioteche digitali e nei sogni di chi ha memoria, si muovono i Custodi della Parola: individui dotati del raro potere di evocare e plasmare la realtà tramite il linguaggio puro. Alcuni li chiamano poeti, altri li considerano hacker dell’inconscio. La verità? Sono l’ultima speranza. Tu sei uno di loro.	HARD	2025-06-13
114	Comunicazione	 Saper esprimere idee in modo chiaro e ascoltare con attenzione può migliorare le relazioni personali e professionali. Spesso non basta parlare: bisogna anche saper leggere tra le righe, comprendere emozioni e contesti. La comunicazione efficace richiede empatia, pazienza e consapevolezza. È una competenza che si può allenare ogni giorno, e che fa davvero la differenza in ogni interazione umana.	MEDIUM	2025-06-13
118	difficolta	 Affrontare le difficoltà con calma e determinazione ci permette di crescere e di conoscerci meglio. Ogni ostacolo è un’opportunità per sviluppare resilienza, adattabilità e consapevolezza di sé. Non sempre possiamo controllare ciò che accade, ma possiamo scegliere come reagire. Coltivare la serenità interiore ci aiuta a mantenere equilibrio anche nei momenti più incerti. È una pratica quotidiana, silenziosa ma potente, che ci rende più forti e autentici.	MEDIUM	2025-06-13
\.


--
-- Data for Name: domanda; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.domanda (testodomanda) FROM stdin;
Quante volte si ripete una parola
Qual è la parola più frequente nel quiz
Qual è la parola più frequente in tutti i documenti
Quale parola non appare mai in nessun documento
\.


--
-- Data for Name: mappa; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.mappa (id, valore, conteggio, documento) FROM stdin;
4951	fare	1	111
4952	lui	1	111
4953	animali	5	111
4954	fiamme	2	111
4955	senza	1	111
4956	del	1	111
4957	insieme	1	111
4958	bocca	1	111
4959	tremavano	1	111
4960	alzò	1	111
4962	potente	1	111
4963	più	1	111
4964	domare	1	111
4965	africana	1	111
4966	il	10	111
4967	avanti	1	111
4968	in	3	111
4969	gli	4	111
4970	lasciandole	1	111
4971	savana	4	111
4972	altri	2	111
4973	come	1	111
4974	nome	1	111
4975	al	1	111
4976	forte	1	111
4977	loro	1	111
4978	volta	1	111
4979	re	1	111
4980	fuoco	1	111
4981	osava	1	111
4982	vasta	1	111
4983	l’eroe	1	111
4984	seguirono	1	111
4985	sua	2	111
4986	insegnare	1	111
4987	«pensi	1	111
4988	risero	1	111
4989	mia	1	111
4990	poi	1	111
4991	tutti	2	111
4992	faccio	1	111
4993	iniziò	1	111
4994	cima	1	111
4995	era	2	111
4996	si	1	111
4997	attenzione	1	111
4998	tutte	1	111
4999	rispettavano	1	111
5000	dargli	1	111
5001	a	2	111
5002	silenzio	1	111
5003	e	8	111
5004	i	1	111
5005	bellezza	1	111
5006	leone	5	111
5007	goccia	1	111
5008	davvero	1	111
5009	mattina	1	111
5010	spegnere	1	111
5011	della	2	111
5012	riuscirono	1	111
5013	fiero	1	111
5014	indietro	1	111
5015	troppo	1	111
5016	la	3	111
5017	c’era	1	111
5018	d’acqua	2	111
5019	becco	1	111
5020	giorno	2	111
5021	le	2	111
5022	da	1	111
5023	enorme	1	111
5024	ammiravano	1	111
5025	con	3	111
5026	lo	2	111
5027	di	4	111
5028	colibrì	3	111
5029	secchiate	1	111
5030	parte	1	111
5031	può	1	111
5032	fiori	1	111
5033	dal	1	111
5034	rispose	1	111
5035	portare	1	111
5036	nel	1	111
5037	cosa	1	111
5038	ma	4	111
5039	sembrava	1	111
5040	fuggivano	1	111
5041	ruggiva	1	111
5042	un	4	111
5043	nonostante	1	111
5044	testa	1	111
5045	che	1	111
5046	nettare	1	111
5047	tra	2	111
5048	ogni	1	111
5049	osservò	1	111
5050	arcobaleno	1	111
5051	degli	1	111
5052	volava	2	111
5053	»	2	111
5054	persino	1	111
5055	direzioni	1	111
5056	avvicinarsi	1	111
5057	raccogliendo	1	111
5058	gocce	1	111
5059	sulle	1	111
5060	noi	1	111
5061	guardava	1	111
5062	qualcosa	1	111
5063	l’incendio	1	111
5064	dolce	1	111
5065	incendio	1	111
5066	anche	2	111
5067	sì	1	111
5068	lumo	4	111
5069	colorato	1	111
5070	ispirati	1	111
5071	sola	1	111
5072	fiume	2	111
5073	scoppiò	1	111
5074	collina	1	111
5075	una	3	111
5076	dalla	1	111
5077	solo	3	111
5078	grande	3	111
5079	nessuno	2	111
5080	piccolo	4	111
5081	imparò	1	111
5082	nutrendosi	1	111
5083	nella	2	111
5084	divenne	1	111
5085	quel	1	111
5086	sapere	1	111
5087	corse	1	111
5088	leggero	1	111
5257	con	1	115
5258	di	2	115
5259	può	2	115
5260	sapori	1	115
5261	ma	1	115
5262	un	1	115
5263	desiderio	1	115
5264	breve	1	115
5265	profondamente	1	115
5266	il	1	115
5267	pregiudizi	1	115
5268	accende	1	115
5269	fisicamente	1	115
5270	ogni	2	115
5271	vissuto	1	115
5272	insegnarci	1	115
5273	spostarsi	1	115
5274	indelebili	1	115
5275	aprire	1	115
5276	sua	1	115
5277	rompe	1	115
5278	lingue	1	115
5279	racconta	1	115
5280	luogo	1	115
5089	smartphone	1	112
5090	con	1	112
5091	di	4	112
5092	circondati	1	112
5093	fondamentale	1	112
5094	affinché	1	112
5095	del	2	112
5096	tuttavia	1	112
5097	insieme	1	112
5100	informazioni	1	112
5101	vivere	1	112
5102	uso	1	112
5103	continua	1	112
5104	possibile	1	112
5105	nostro	1	112
5106	un	2	112
5107	bilanciamento	1	112
5108	rapidamente	1	112
5109	modo	1	112
5110	profondamente	1	112
5111	più	1	112
5112	che	2	112
5113	trasformato	1	112
5114	promuovere	1	112
5115	tra	1	112
5116	il	2	112
5117	distribuiti	1	112
5118	ogni	1	112
5119	ai	1	112
5120	siamo	1	112
5121	semplificare	1	112
5122	come	1	112
5123	artificiale	1	112
5124	creino	1	112
5125	tecnologia	2	112
5126	degli	1	112
5127	quotidiane	1	112
5128	attraverso	1	112
5129	disuguaglianza	1	112
5130	contatti	1	112
5131	comunicare	1	112
5132	etica	1	112
5133	tutela	1	112
5134	persone	1	112
5135	intelligenti	1	112
5136	l’adattamento	1	112
5137	siano	1	112
5138	privacy	1	112
5139	alle	1	112
5140	non	1	112
5141	numerosi	1	112
5142	ciò	1	112
5143	consapevole	1	112
5144	dispositivi	1	112
5145	molte	1	112
5146	emergono	1	112
5147	potremo	1	112
5148	attività	1	112
5149	educazione	1	112
5150	vita	1	112
5151	sostenibile	1	112
5152	collaborazione	1	112
5153	anche	1	112
5154	equamente	1	112
5155	benefici	1	112
5156	lontane	1	112
5157	a	1	112
5158	mantenere	1	112
5159	e	6	112
5160	mercato	1	112
5161	l’innovazione	1	112
5162	ci	1	112
5163	è	2	112
5164	digitale	2	112
5165	i	3	112
5166	solo	1	112
5167	forme	1	112
5168	della	2	112
5169	confini	1	112
5170	accedere	1	112
5171	lavoro	1	112
5172	reale	1	112
5173	sfide	1	112
5174	nuove	2	112
5175	ridefinire	1	112
5176	vantaggi	1	112
5177	la	2	112
5178	futuro	1	112
5179	giorno	1	112
5180	lavorare	1	112
5181	costruire	1	112
5182	ha	1	112
5183	permettono	1	112
5184	quindi	1	112
5185	da	1	112
5281	viaggio	1	115
5282	storia	1	115
5283	significa	1	115
5284	lasciare	1	115
5285	non	1	115
5286	curiosità	1	115
5287	qualcosa	1	115
5288	se	1	115
5289	anche	2	115
5290	incontro	1	115
5291	trasformarci	1	115
5292	a	1	115
5293	e	4	115
5294	consapevolezza	1	115
5295	stimola	1	115
5296	mente	1	115
5297	i	1	115
5298	una	1	115
5299	solo	1	115
5300	ricordi	1	115
5301	nuove	1	115
5302	prezioso	1	115
5303	la	2	115
5304	culture	1	115
5305	scoperta	1	115
5306	arricchisce	1	115
5307	viaggiare	2	115
5308	paesaggi	1	115
5361	cammino	1	117
5362	verità	1	117
5363	seguire	1	117
5364	rumore	1	117
5365	la	1	117
5366	cui	1	117
5367	traccia	1	117
5368	scelta	1	117
5369	quanto	1	117
5098	intelligenza	1	112
5099	avvento	1	112
4961	io	1	111
5186	forza	1	113
5187	riflettere	1	113
5188	più	1	113
5189	a	1	113
5190	interiore	1	113
5191	silenzio	1	113
5192	mondo	1	113
5193	il	1	113
5194	e	1	113
5195	in	1	113
5196	di	2	113
5197	è	1	113
5198	dice	1	113
5199	frenetico	1	113
5200	parole	1	113
5201	profonda	1	113
5202	segno	1	113
5203	fermare	1	113
5204	volte	1	113
5205	mille	1	113
5206	saperci	1	113
5207	un	2	113
5208	ascoltare	1	113
5309	senza	1	116
5310	tempo	1	116
5311	aspettare	1	116
5312	non	1	116
5313	saper	2	116
5314	sa	1	116
5315	potere	1	116
5316	ma	1	116
5317	pazienza	1	116
5318	raro	1	116
5319	un	1	116
5320	anche	1	116
5321	accogliere	1	116
5322	attendere	1	116
5323	che	1	116
5324	il	1	116
5325	e	1	116
5326	chi	1	116
5327	in	1	116
5328	possiede	1	116
5329	passa	1	116
5330	è	1	116
5331	solo	1	116
5332	ansia	1	116
5333	prezioso	1	116
5334	un’epoca	1	116
5335	la	1	116
5336	impaziente	1	116
5370	con	1	118
5371	scegliere	1	118
5372	crescere	1	118
5373	di	3	118
5374	incerti	1	118
5375	controllare	1	118
5376	nei	1	118
5377	ma	2	118
5378	sempre	1	118
5379	conoscerci	1	118
5380	potente	1	118
5381	resilienza	1	118
5382	per	1	118
5383	più	2	118
5384	che	2	118
5385	accade	1	118
5386	ogni	1	118
5387	quotidiana	1	118
5388	rende	1	118
5389	come	1	118
5390	serenità	1	118
5391	forti	1	118
5392	un’opportunità	1	118
5393	affrontare	1	118
5394	coltivare	1	118
5395	permette	1	118
5396	interiore	1	118
5397	calma	1	118
5398	possiamo	2	118
5399	reagire	1	118
5400	adattabilità	1	118
5401	ostacolo	1	118
5402	momenti	1	118
5403	autentici	1	118
5404	non	1	118
5405	ciò	1	118
5406	silenziosa	1	118
5407	sviluppare	1	118
5408	sé	1	118
5409	anche	1	118
5410	meglio	1	118
5411	a	1	118
5412	difficoltà	1	118
5413	mantenere	1	118
5414	e	4	118
5415	consapevolezza	1	118
5416	ci	3	118
5417	è	2	118
5418	una	1	118
5419	aiuta	1	118
5420	equilibrio	1	118
5421	determinazione	1	118
5422	la	1	118
5423	le	1	118
5424	pratica	1	118
4832	finchè	1	110
4825	inconscio	1	110
4812	memoria	1	110
4813	chiamarsi	1	110
4814	vendicativi	1	110
4815	dei	1	110
4816	del	1	110
4817	vincendo	1	110
4818	sogni	1	110
4819	divora	1	110
4820	semantico	1	110
4821	spiriti	1	110
4822	lessicale	1	110
4823	grigia	1	110
4824	stati	1	110
4826	più	1	110
4827	il	4	110
4828	in	2	110
4829	considerano	1	110
4830	codice	1	110
4831	sono	4	110
4833	altri	1	110
4834	nome	1	110
4835	mute	1	110
4836	come	1	110
4837	al	1	110
4838	corrotti	1	110
4839	nomi	1	110
4840	loro	2	110
4841	disconnessi	1	110
4842	puro	1	110
4843	afasiche	1	110
4844	venne	1	110
4845	custodi	1	110
4846	ruppe	1	110
4847	chiamano	1	110
4848	stanno	1	110
4849	impazzite	1	110
4850	plasmare	1	110
4851	parola	3	110
4852	diventate	1	110
4853	poi	1	110
4854	sa	1	110
4855	realtà	2	110
4856	diventati	1	110
4857	coscienza	1	110
4858	materializzate	1	110
4859	mai	1	110
4860	tramite	1	110
4861	sei	1	110
4862	si	5	110
4863	nate	1	110
4864	vita	1	110
4865	tutto	1	110
4866	dotati	1	110
4867	pensieri	1	110
4868	stirò	1	110
4869	resti	1	110
4870	resistenza	1	110
4871	poeti	1	110
4872	imprecisato	1	110
4873	e	5	110
4874	i	5	110
4875	calendari	1	110
4876	abbreviazioni	1	110
4877	speranza	1	110
4878	della	2	110
4879	signori	1	110
4880	hacker	1	110
4881	digitali	1	110
4882	la	6	110
4883	le	2	110
4884	significati	1	110
4885	li	2	110
4886	battitura	1	110
4887	da	1	110
4888	giorni	1	110
4889	biblioteche	1	110
4890	concluse	1	110
4891	muovono	1	110
4892	tu	1	110
4893	di	6	110
4894	ancora	1	110
4895	nacque	1	110
4896	dal	1	110
4897	zona	1	110
4898	potere	1	110
4900	nei	2	110
4901	evocare	1	110
4902	ma	1	110
4903	contorse	1	110
4904	cosa	2	110
4905	nel	1	110
4906	zone	1	110
4907	linguaggio	3	110
4909	manipolazioni	1	110
4910	raro	1	110
4911	un	1	110
4912	città	1	110
4913	che	3	110
4914	stesso	1	110
4915	individui	1	110
4916	chi	1	110
4917	ogni	2	110
4918	mostri	1	110
4919	collasso	1	110
4920	piegò	1	110
4921	dell’oblio	1	110
4922	verità	1	110
4923	errori	2	110
4924	mezzo	1	110
4925	cancellati	1	110
4926	vuoto	2	110
4927	storia	1	110
4928	non	1	110
4929	vecchie	1	110
4930	infestano	1	110
4931	fu	1	110
4932	emergono	1	110
4933	rete	2	110
4934	entità	1	110
4935	principio	1	110
4936	ora	1	110
4937	perduto	1	110
4938	costruì	1	110
4939	è	1	110
4940	una	2	110
4941	nessuno	1	110
4942	forme	1	110
4943	accelerò	1	110
4944	uno	1	110
4945	frasi	1	110
4946	ha	2	110
4947	civiltà	1	110
4948	alcuni	1	110
4949	quel	1	110
4950	l’ultima	1	110
5209	con	1	114
5210	emozioni	1	114
5211	differenza	1	114
5212	migliorare	1	114
5213	leggere	1	114
5214	professionali	1	114
5215	idee	1	114
5216	allenare	1	114
5217	non	1	114
5218	può	2	114
5219	saper	2	114
5220	bisogna	1	114
5221	basta	1	114
5222	si	1	114
5223	competenza	1	114
5224	pazienza	1	114
5225	comunicazione	1	114
5226	interazione	1	114
5227	anche	1	114
5228	attenzione	1	114
5229	ascoltare	1	114
5230	modo	1	114
5231	umana	1	114
5232	chiaro	1	114
5233	esprimere	1	114
5234	che	2	114
5235	tra	1	114
5236	e	5	114
5237	in	2	114
5238	consapevolezza	1	114
5239	ogni	2	114
5240	è	1	114
5241	una	1	114
5242	relazioni	1	114
5243	comprendere	1	114
5244	davvero	1	114
5245	parlare	1	114
5246	spesso	1	114
5247	efficace	1	114
5248	personali	1	114
5249	la	2	114
5250	richiede	1	114
5251	giorno	1	114
5252	le	2	114
5253	contesti	1	114
5254	righe	1	114
5255	fa	1	114
5256	empatia	1	114
5337	direzione	1	117
5338	atto	1	117
5339	di	3	117
5340	del	1	117
5341	ciò	1	117
5342	avere	1	117
5343	coraggio	1	117
5344	distrazioni	1	117
5345	pieno	1	117
5346	nostro	1	117
5347	un	2	117
5348	per	1	117
5349	facciamo	1	117
5350	coerenza	1	117
5351	che	1	117
5352	il	1	117
5353	mondo	1	117
5354	piccola	1	117
5355	e	2	117
5356	in	2	117
5357	ogni	1	117
5358	è	1	117
5359	rara	1	117
5360	crediamo	1	117
4899	anno	1	110
5492	camminava	1	120
5493	con	1	120
5494	strade	1	120
5495	alba	2	120
5496	tempo	1	120
5497	portando	1	120
5498	antica	2	120
5499	pietre	1	120
5500	dal	1	120
5501	consumate	1	120
5502	eco	2	120
5503	rompeva	1	120
5504	nel	1	120
5505	illuminava	1	120
5506	passata	1	120
5507	richiamo	1	120
5508	luce	1	120
5509	cuore	1	120
5510	nascondeva	1	120
5511	città	2	120
5512	all	1	120
5513	usignolo	1	120
5514	tra	1	120
5515	il	7	120
5516	in	1	120
5517	chi	1	120
5518	odore	1	120
5519	ogni	1	120
5520	percepire	1	120
5521	poteva	1	120
5522	rimbalzava	1	120
5523	umidità	1	120
5524	ombra	1	120
5525	epoca	1	120
5526	mescolava	1	120
5527	nell	1	120
5528	strette	1	120
5529	orizzonte	1	120
5530	avventura	1	120
5531	identità	1	120
5532	vicoli	1	120
5533	profumo	1	120
5534	mentre	1	120
5535	si	1	120
5536	sé	1	120
5537	immortalità	1	120
5538	angolo	1	120
5539	lontane	1	120
5540	forza	1	120
5541	silenzio	1	120
5542	avvolgeva	1	120
5543	mura	1	120
5544	incenso	1	120
5545	segreto	1	120
5546	e	2	120
5547	voci	1	120
5548	i	1	120
5549	canto	1	120
5550	l	1	120
5551	dell	13	120
5552	quella	1	120
5553	delle	1	120
5554	la	2	120
5555	nascosta	1	120
5556	le	3	120
5557	anima	1	120
5558	mistero	1	120
5559	respiro	1	120
5560	nascosti	1	120
\.


--
-- Data for Name: sessione; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.sessione (id, punteggio, utente, difficolta) FROM stdin;
\.


--
-- Data for Name: stopwords; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.stopwords (id, parola) FROM stdin;
36	il
37	lo
38	la
39	i
40	gli
41	le
42	un
43	uno
44	una
45	del
46	dello
47	della
48	dei
49	degli
50	delle
51	al
52	allo
53	alla
54	ai
55	agli
56	alle
57	dal
58	dallo
59	dalla
60	dai
61	dagli
62	dalle
63	nel
64	nello
65	nella
66	nei
67	negli
68	nelle
69	sul
70	sullo
71	sulla
72	sui
73	sugli
74	sulle
75	con
76	su
77	per
78	tra
79	fra
80	e
81	ma
82	o
83	anche
84	come
85	se
86	che
87	di
88	a
89	da
90	è
91	non
92	ci
93	ogni
94	può
95	dell
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (username, password, admin) FROM stdin;
vincenzo	Salernitana2003	t
gianmarco	VivaViva10	t
\.


--
-- Data for Name: vocabolario; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.vocabolario (stringa) FROM stdin;
esempio
cane
gatto
lupo
gian
libano
salerno
napoli
pozzuoli
benevento
citta
garage
carne
pesce
ineffabile
atavico
diacronico
palinsesto
epistemico
ipostasi
apodittico
prolessi
paradigma
entropia
solipsismo
topos
elucubrazione
demiurgo
nefelibata
acribia
parossismo
metempsicosi
anacoluto
flatus 
\.


--
-- Name: documento_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.documento_id_seq', 1, false);


--
-- Name: documento_id_seq1; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.documento_id_seq1', 120, true);


--
-- Name: parola_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.parola_id_seq', 5560, true);


--
-- Name: sessione_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.sessione_id_seq', 50, true);


--
-- Name: stopwords_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.stopwords_id_seq', 95, true);


--
-- Name: documento documento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.documento
    ADD CONSTRAINT documento_pkey PRIMARY KEY (id);


--
-- Name: domanda domanda_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.domanda
    ADD CONSTRAINT domanda_pk PRIMARY KEY (testodomanda);


--
-- Name: mappa parola_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mappa
    ADD CONSTRAINT parola_pkey PRIMARY KEY (id);


--
-- Name: sessione sessione_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sessione
    ADD CONSTRAINT sessione_pkey PRIMARY KEY (id);


--
-- Name: stopwords stopwords_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.stopwords
    ADD CONSTRAINT stopwords_pk PRIMARY KEY (id);


--
-- Name: users username; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT username PRIMARY KEY (username);


--
-- Name: vocabolario vocabolario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vocabolario
    ADD CONSTRAINT vocabolario_pkey PRIMARY KEY (stringa);


--
-- Name: mappa parola_documento_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mappa
    ADD CONSTRAINT parola_documento_fkey FOREIGN KEY (documento) REFERENCES public.documento(id) ON DELETE CASCADE;


--
-- Name: sessione sessione_utente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sessione
    ADD CONSTRAINT sessione_utente_fkey FOREIGN KEY (utente) REFERENCES public.users(username) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--

