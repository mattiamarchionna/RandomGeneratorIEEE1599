package com.random.generator;

import javafx.scene.input.PickResult;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.print.Doc;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.lang.annotation.ElementType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.Random;

public class GeneratorIEEE1599 {
    private String path;
    private String nameOfFile;
    public Parameter configuration;
    private String[] formats = {"application_excel", "application_mac-binhex40", "application_msword", "application_octet-stream", "application_pdf", "application_x-director", "application_x-gzip", "application_x-javascript", "application_x-macbinary", "application_x-pn-realaudio",  "application_x-shockwave_flash", "application_x-tar", "application_zip", "audio_aiff", "audio_avi", "audio_mp3", "audio_mpeg", "audio_mpeg3", "audio_mpg", "audio_wav", "audio_x_aiff", "audio_x_midi", "audio_x_wav", "audio_x-mp3", "audio_x-mpeg", "audio_x-mpeg3", "audio_x-mpegaudio", "audio_x-mpg", "audio_x-ms-wma", "image_avi", "image_bmp", "image_x-bmp", "image_x-bitmap", "image_x-xbitmap", "image_x-win-bitmap", "image_x-windows-bmp", "image_ms-bmp", "image_x-ms-bmp", "application_bmp", "application_x-bmp", "application_x-win-bitmap", "application_preview", "image_gif", "image_jpeg", "image_pict", "image_png","application_png","application_x-png","image_tiff","text_html","text_plain_application_postscript","video_avi","video_mpeg","video_msvideo" ,"video_quicktime" ,"video_x-msvideo", "video_x-ms-wmv" ,"video_x-qtc", "video_xmpg2"};





    public GeneratorIEEE1599(String path, String nameOfFile, Parameter configuration){
        this.path = path;
        this.nameOfFile = nameOfFile;
        this.configuration = configuration;
    }


    public void generate_file(){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            save_xml_file(builder.newDocument(), "new"); // inizializzazione dell'XML 'vuoto'
            File file = new File(this.path + this.nameOfFile);
            Document doc = builder.parse(file);
            Element root = doc.getDocumentElement();
            //System.out.println("Il nodo radice e': " + root.getNodeName());

            for(int i = 0; i < 10; i++){
                Element event = generate_random_event(doc, i);
                root.appendChild(event);

            }
            //esempio generazione elemento nota
            for(int i = 0; i < configuration.getLenght(); i++){
                Element chord = generate_random_chord(doc, i, root.getElementsByTagName("event"));
                root.appendChild(chord);
            }


            save_xml_file(doc, "save");
        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println(e);
            System.out.println("Errore nell'elaborazione del file");
            System.exit(1);
        }
    }


    private boolean isGenerateElement(){
        Random rand = new Random();
        return rand.nextBoolean();
    }

    private String generate_date() {
        Random random = new Random();
        int minDay = (int) LocalDate.of(1900, 1, 1).toEpochDay();
        int maxDay = (int) LocalDate.of(2018, 1, 1).toEpochDay();
        long randomDay = minDay + random.nextInt(maxDay - minDay);
        LocalDate randomBirthDate = LocalDate.ofEpochDay(randomDay);
        return randomBirthDate.toString();
    }


    
    private Element generate_random_midi_event_sequence(Document doc, NodeList events) {
        Element midi_event_sequence = doc.createElement("midi_event_sequence");
        String[] type = {"metrical", "timecode"};
        String[] unit = {"ticks", "sec"};
        Random r = new Random();
        midi_event_sequence.setAttribute("division_value", String.valueOf(r.nextFloat()).substring(0, 5));
        midi_event_sequence.setAttribute("type", type[r.nextInt(type.length)]);
        midi_event_sequence.setAttribute("measurement_unit", unit[r.nextInt(unit.length)]);
        return midi_event_sequence;
    }

    private Element generate_random_midi_event(Document doc, NodeList events){
        Element midi_event = doc.createElement("midi_event");
        ArrayList<String> idsEvents = new ArrayList<>(getIds(events));
        Random r = new Random();
        midi_event.setAttribute("event_ref", idsEvents.get(r.nextInt(idsEvents.size())));
        midi_event.setAttribute("timing", "timing_" + r.nextInt(100));
        return midi_event;
    }

    private Element generate_random_metronomic_indication(Document doc, NodeList events){
        Random r = new Random();
        Element metronomic_indication = doc.createElement("metronomic_indication");
        metronomic_indication.setAttribute("num", String.valueOf(r.nextInt(5) + 1));
        metronomic_indication.setAttribute("den", String.valueOf(r.nextInt(7) + 1));
        metronomic_indication.setAttribute("value", String.valueOf(r.nextInt(200)));
        metronomic_indication.setAttribute("dots", String.valueOf(r.nextInt(200)));
        ArrayList<String> idsEvents = new ArrayList<>(getIds(events));
        metronomic_indication.setAttribute("event_ref", idsEvents.get(r.nextInt(idsEvents.size())));
        return metronomic_indication;
    }

    private Element generate_random_measure_repeat(Document doc, NodeList events) {
        Element measure_repeat = doc.createElement("measure_repeat");
        Random rand = new Random();
        if (isGenerateElement()) {
            ArrayList<String> idEvents = getIds(events);
            measure_repeat.setAttribute("event_ref", idEvents.get(rand.nextInt(idEvents.size())));
        }
        measure_repeat.setAttribute("number_of_measures", String.valueOf(rand.nextInt(100)));
        return measure_repeat;
    }

    private Element generate_random_measure(Document doc, int id){
        Element measure = doc.createElement("measure");
        if(isGenerateElement()) measure.setAttribute("id", String.valueOf(id));
        Random rand = new Random();
        measure.setAttribute("number", String.valueOf(rand.nextInt(100)));
        String[] show = {"yes", "no"};
        String[] style = {"arabic_numbers", "roman_number", "small_letters", "capital_letters"};
        if(isGenerateElement()) measure.setAttribute("show_number", show[rand.nextInt(show.length)]);
        if(isGenerateElement()) measure.setAttribute("numbering_style", style[rand.nextInt(style.length)]);
        return measure;
    }

    private Element generate_random_main_title(Document doc){
        Element main_title = doc.createElement("main_title");
        main_title.setTextContent("title_" + new Random().nextInt(20));
        return main_title;
    }

    private Element generate_random_lyrics(Document doc, NodeList parts, NodeList voices){
        Element lyrics = doc.createElement("lyrics");
        ArrayList<String> partIds = getIds(parts);
        ArrayList<String> voiceIds = getIds(voices);
        Random rand = new Random();

        lyrics.setAttribute("part_ref", partIds.get(rand.nextInt(partIds.size())));
        lyrics.setAttribute("voice_ref", voiceIds.get(rand.nextInt(voiceIds.size())));
        return lyrics;
    }

    private Element generate_random_los(Document doc){
        return doc.createElement("los");
    }

    private Element generate_random_logic(Document doc){
        return doc.createElement("logic");
    }

    private Element generate_random_layout(Document doc){
        String[] measurement_unit = {"centimeters", "millimeters", "inches", "decimal_inches", "points", "picas", "pixels", "twips"};
        Random rand = new Random();
        Element layout = doc.createElement("layout");
        layout.setAttribute("hpos_per_unit", String.valueOf(rand.nextInt(20)));
        layout.setAttribute("measurement_unit", measurement_unit[rand.nextInt(measurement_unit.length)]);
        return layout;
    }

    private Element generate_random_layout_system(Document doc, int id){
        Element layout_system = doc.createElement("layout_system");
        if(isGenerateElement()) layout_system.setAttribute("id", String.valueOf(id));
        Random rand = new Random();
        layout_system.setAttribute("upper_left_x", String.valueOf(rand.nextInt(20)));
        layout_system.setAttribute("upper_left_y", String.valueOf(rand.nextInt(20)));
        layout_system.setAttribute("lower_right_x", String.valueOf(rand.nextInt(20)));
        layout_system.setAttribute("lower_right_x", String.valueOf(rand.nextInt(20)));
        return layout_system;
    }

    private Element generate_random_layout_staff(Document doc, int id, NodeList staffs){
        Element layout_staff = doc.createElement("layout_staff");
        if(isGenerateElement()) layout_staff.setAttribute("id", String.valueOf(id));
        ArrayList<String> idStaffs = getIds(staffs);
        String[] values = {"yes", "no"};
        Random rand = new Random();
        layout_staff.setAttribute("staff_ref", idStaffs.get(new Random().nextInt(idStaffs.size())));
        layout_staff.setAttribute("vertical_offset", String.valueOf(rand.nextInt(20)));
        layout_staff.setAttribute("height", String.valueOf(rand.nextInt(20)));
        layout_staff.setAttribute("ossia", values[rand.nextInt(values.length)]);
        layout_staff.setAttribute("show_time_signature", values[rand.nextInt(values.length)]);
        layout_staff.setAttribute("show_key_clef", values[rand.nextInt(values.length)]);
        layout_staff.setAttribute("show_key_signature", values[rand.nextInt(values.length)]);
        return layout_staff;
    }

    private Element generate_random_layout_shapes(Document doc) {
        Element layout_shapes = doc.createElement("layout_shapes");
        Random rand = new Random();
        layout_shapes.setAttribute("horizontal_offset", String.valueOf(rand.nextInt(20)));
        layout_shapes.setAttribute("vertical_offset", String.valueOf(rand.nextInt(20)));
        return layout_shapes;
    }

    private Element generate_random_layout_images(Document doc) {
        Element layout_images = doc.createElement("layout_images");
        Random rand = new Random();
        layout_images.setAttribute("file_name", "file_" + rand.nextInt(50));
        layout_images.setAttribute("file_format", this.formats[rand.nextInt(formats.length)]);
        layout_images.setAttribute("encoding_format", this.formats[rand.nextInt(formats.length)]);
        layout_images.setAttribute("horizontal_offset", String.valueOf(rand.nextInt(20)));
        layout_images.setAttribute("vertical_offset", String.valueOf(rand.nextInt(20)));
        if (isGenerateElement()) layout_images.setAttribute("description", "description_" + rand.nextInt(40));
        if (isGenerateElement()) layout_images.setAttribute("copyright", "copyright_" + rand.nextInt(40));
        if (isGenerateElement()) layout_images.setAttribute("notes", "notes_" + rand.nextInt(40));
        return layout_images;
    }

    private Element generate_random_custom_key_signature(Document doc, NodeList events){
        String[] accidental = {"none", "double_flat", "flat_and_a_half", "flat", "demiflat", "natural", "demisharp", "sharp", "sharp_and_a_half", "double_sharp"};
        Element custom_key_signature = doc.createElement("custom_key_signature");
        ArrayList<String> idEvents = getIds(events);
        Random rand = new Random();
        custom_key_signature.setAttribute("event_ref", idEvents.get(new Random().nextInt(idEvents.size())));

        int number_key_accidental = rand.nextInt(10);
        String[] steps = {"A", "B", "C", "D", "E", "F", "G"};
        for(int i = 0; i < number_key_accidental; i++){
            Element key_accidental = doc.createElement("key_accidental");
            key_accidental.setAttribute("step", steps[rand.nextInt(steps.length)]);
            key_accidental.setAttribute("accidental", accidental[rand.nextInt(accidental.length)]);
            custom_key_signature.appendChild(key_accidental);
        }
        return custom_key_signature;
    }

    private Element generate_random_key_signature(Document doc, int id, NodeList events){
        Element key_signature = doc.createElement("key_signature");
        ArrayList<String> idEvents = getIds(events);
        Random rand = new Random();
        key_signature.setAttribute("event_ref", idEvents.get(new Random().nextInt(idEvents.size())));
        Element num;
        if(isGenerateElement()) num = doc.createElement("sharp_num");
        else num = doc.createElement("flat_num");
        String[] number = {"0", "1", "2", "3", "4", "5", "6", "7"};
        num.setAttribute("number", number[rand.nextInt(number.length)]);
        key_signature.appendChild(num);

        return key_signature;
    }

    private Element generate_random_key(Document doc, int id, NodeList staffs){
        Element key = doc.createElement("key");
        if(isGenerateElement()) key.setAttribute("id", String.valueOf(id));
        ArrayList<String> idStaffs = getIds(staffs);
        if(isGenerateElement()) key.setAttribute("staff_ref", idStaffs.get(new Random().nextInt(idStaffs.size())));
        return key;
    }

    private Element generate_random_jump_to(Document doc, int id, NodeList events){
        Element jump_to = doc.createElement("jump_to");
        if(isGenerateElement()) jump_to.setAttribute("id", String.valueOf(id));
        ArrayList<String> idEvents = getIds(events);
        jump_to.setAttribute("event_ref", idEvents.get(new Random().nextInt(idEvents.size())));
        return jump_to;
    }

    private Element generate_random_horizontal_symbols(Document doc){
        return doc.createElement("horizontal_symbols");
    }

    private Element generate_random_hairpin(Document doc, int id, NodeList events, NodeList staffs){
        Element hairpin = doc.createElement("hairpine");
        Random rand = new Random();
        ArrayList<String> idEvents = getIds(events);
        if(isGenerateElement()) hairpin.setAttribute("id", String.valueOf(id));
        hairpin.setAttribute("start_event_ref", idEvents.get(rand.nextInt(idEvents.size())));
        hairpin.setAttribute("end_event_ref", idEvents.get(rand.nextInt(idEvents.size())));
        ArrayList<String> idStaffs = getIds(staffs);
        if(isGenerateElement()) hairpin.setAttribute("staff_ref", idStaffs.get(rand.nextInt(idStaffs.size())));
        String[] type = {"crescendo", "diminuendo"};
        hairpin.setAttribute("type", type[rand.nextInt(type.length)]);
        return hairpin;
    }


    private Element generate_random_gregorian_symbol(Document doc, int id, NodeList events){
        Element gregorian_symbol = doc.createElement("gregorian_symbol");
        Random rand = new Random();
        String[] neume = ("punctum,virga,punctum_inclinatum,quilisma,apostrofa |\n" +
                "               oriscus,podatus,pes,clivis,flexa,epiphonus |\n" +
                "               cephalicus,bistropha,bivirga,trigon,torculus |\n" +
                "               porrectus,scandicus,salicus,climacus,tristropha |\n" +
                "               trivirga,strophicus,pressus,custos").split(",");

        String[] subtripunctis = ("no,praepunctis,subpunctis,subbipunctis,subtripunctis,subquadripunctis,subquinquipunctis").split(",");
        String[] interpretative_mark = ("no,vertical_episema,horizontal_episema,liquescens").split(",");
        String[] mora = {"yes", "no"};
        String[] inflextion = ("no,resupinus,flexus").split(",");
        if(isGenerateElement()) gregorian_symbol.setAttribute("id", String.valueOf(id));
        ArrayList<String> idEvents = getIds(events);
        gregorian_symbol.setAttribute("event_ref", idEvents.get(rand.nextInt(idEvents.size())));
        gregorian_symbol.setAttribute("neume", neume[rand.nextInt(neume.length)]);
        gregorian_symbol.setAttribute("subtripunctis", subtripunctis[rand.nextInt(subtripunctis.length)]);
        gregorian_symbol.setAttribute("interpretative_mark", interpretative_mark[rand.nextInt(interpretative_mark.length)]);
        gregorian_symbol.setAttribute("mora", mora[rand.nextInt(mora.length)]);
        gregorian_symbol.setAttribute("inflextion", inflextion[rand.nextInt(inflextion.length)]);
        return gregorian_symbol;
    }

    private Element generate_random_graphic_instance_group(Document doc){
        Element graphic_instance_group = doc.createElement("graphic_instance_group");
        graphic_instance_group.setAttribute("description", "description_" + new Random().nextInt(30));
        return  graphic_instance_group;
    }

    private Element generate_random_graphic_instance(Document doc, NodeList events){
        Random rand = new Random();
        Element graphic_instance = doc.createElement("graphic_instance");
        String[] measurement_unit = {"centimeters", "millimeters", "inches", "decimal_inches", "points", "picas", "pixels", "twips"};
        if(isGenerateElement()) graphic_instance.setAttribute("description", "description" + rand.nextInt(20));
        graphic_instance.setAttribute("position_in_group", "position_" + rand.nextInt(50));
        graphic_instance.setAttribute("file_name", "file_" + rand.nextInt(50));
        graphic_instance.setAttribute("position_in_group", "position_" + rand.nextInt(50));

        graphic_instance.setAttribute("file_format", formats[rand.nextInt(formats.length)]);
        graphic_instance.setAttribute("encoding_format", formats[rand.nextInt(formats.length)]);
        graphic_instance.setAttribute("measurement_unit", measurement_unit[rand.nextInt(measurement_unit.length)]);
        return graphic_instance;
    }


    private Element generate_random_graphic_event(Document doc, NodeList events){
        Element graphic_event = doc.createElement("graphic_event");
        Random rand = new Random();
        ArrayList<String> idEvents = getIds(events);
        graphic_event.setAttribute("event_ref", idEvents.get(rand.nextInt(idEvents.size())));
        graphic_event.setAttribute("upper_left_x", String.valueOf(rand.nextInt(20)));
        graphic_event.setAttribute("upper_left_y", String.valueOf(rand.nextInt(20)));
        graphic_event.setAttribute("lower_right_x", String.valueOf(rand.nextInt(20)));
        graphic_event.setAttribute("lower_right_x", String.valueOf(rand.nextInt(20)));
        if(isGenerateElement())  graphic_event.setAttribute("highlight_color", "color_" + rand.nextInt(255));
        if(isGenerateElement()) graphic_event.setAttribute("description", "description" + rand.nextInt(20));
        return graphic_event;
    }

    private Element generate_random_glissando(Document doc, int id, NodeList events){
        Element glissando = doc.createElement("glissando");
        Random rand = new Random();
        ArrayList<String> idEvents = getIds(events);
        if(isGenerateElement()) glissando.setAttribute("id", String.valueOf(id));
        glissando.setAttribute("start_event_ref", idEvents.get(rand.nextInt(idEvents.size())));
        if(isGenerateElement()) glissando.setAttribute("end_event_ref", idEvents.get(rand.nextInt(idEvents.size())));
        return glissando;
    }

    private Element generate_random_genres(Document doc){
        Random rand = new Random();
        Element genres = doc.createElement("genres");
        int number_genre = rand.nextInt(10);
        for(int i = 0; i < number_genre; i++) {
            Element genre = doc.createElement("genre");
            genre.setAttribute("name", "name_" + rand.nextInt(20));
            if (isGenerateElement()) genre.setAttribute("description", "description_" + rand.nextInt(20));
            if (isGenerateElement()) genre.setAttribute("weight", "weight_" + rand.nextInt(20));
            genres.appendChild(genre);
        }
        return genres;
    }

    private Element generate_random_general(Document doc){
        return doc.createElement("general");
    }

    private Element generate_random_fingering(Document doc){
        Element fingering = doc.createElement("fingering");
        String[] number = {"1", "2", "3", "4", "5"};
        fingering.setAttribute("number", number[new Random().nextInt(number.length)]);
        return  fingering;
    }

    private Element generate_random_fermata(Document doc, int id, NodeList events){
        Element fermata = doc.createElement("fermata");
        Random rand = new Random();
        if(isGenerateElement()) fermata.setAttribute("id", String.valueOf(id));

        ArrayList<String> idEvents = getIds(events);
        fermata.setAttribute("event_ref", idEvents.get(rand.nextInt(idEvents.size())));

        return fermata;
    }

    private Element generate_random_relationship(Document doc, int id, NodeList segments, NodeList feature_objects, NodeList feature_object_relationship){
        Element relationships = doc.createElement("relationships");
        Random rand = new Random();
        relationships.setAttribute("id", String.valueOf(id));
        ArrayList<String> segment_refs = getIds(segments);
        ArrayList<String> feature_object_refs = getIds(feature_objects);
        ArrayList<String> feature_object_relationship_ref = getIds(feature_object_relationship);

        if(isGenerateElement()) relationships.setAttribute("description", "description_" + rand.nextInt(20));
        relationships.setAttribute("segment_a_ref", segment_refs.get(rand.nextInt(segment_refs.size())));
        relationships.setAttribute("segment_b_ref", segment_refs.get(rand.nextInt(segment_refs.size())));
        if(isGenerateElement()) relationships.setAttribute("feature_object_a_ref", feature_object_refs.get(rand.nextInt(feature_object_refs.size())));
        if(isGenerateElement()) relationships.setAttribute("feature_object_b_ref", feature_object_refs.get(rand.nextInt(feature_object_refs.size())));

        if(isGenerateElement()) relationships.setAttribute("feature_object_relationship_ref", feature_object_relationship_ref.get(rand.nextInt(feature_object_relationship_ref.size())));

        return relationships;
    }


    private Element generate_random_feature_object_relationships(Document doc){
        Random rand = new Random();
        Element feature_object_relationships = doc.createElement("feature_object_relationships");

        int number_relationship = rand.nextInt(10);
        for(int i = 0; i < number_relationship; i++){
            Element feature_object_relationship = doc.createElement("feature_object_relationship");
            feature_object_relationship.setAttribute("id", String.valueOf(i));
            Element ver_rule = doc.createElement("ver_rule");
            feature_object_relationship.appendChild(ver_rule);
            feature_object_relationships.appendChild(feature_object_relationship);
        }
        return feature_object_relationships;
    }


    private Element generate_feature_object(Document doc, int id){
        Random rand = new Random();
        Element feature_object = doc.createElement("feature_object");
        if(isGenerateElement()) feature_object.setAttribute("id", String.valueOf(id));
        feature_object.setAttribute("name", "name_" + rand.nextInt(50));
        return feature_object;
    }

    private Element generate_random_event(Document doc, int id){
        Random rand = new Random();
        Element event = doc.createElement("event");
        event.setAttribute("id", "event" + String.valueOf(id));
        event.setAttribute("timing", String.valueOf(rand.nextInt(256)));
        event.setAttribute("hpos", String.valueOf(rand.nextInt(256)));
        return event;
}

    private Element generate_random_end(Document doc, int id, NodeList events){
        Element end = doc.createElement("ref");
        Random rand = new Random();
        ArrayList<String> idEvents = getIds(events);
        if(isGenerateElement()) end.setAttribute("id", String.valueOf(id));
        end.setAttribute("event_ref", idEvents.get(rand.nextInt(idEvents.size())));
        return end;
    }

    private Element generate_random_dynamic(Document doc, int id, NodeList events, NodeList staffs){
        Random rand = new Random();
        String[] extension_line_shape = {"normal", "dotted", "dashed"};
        Element dynamic = doc.createElement("dynamic");
        ArrayList<String> idEvents = getIds(events);
        ArrayList<String> idStaffs = getIds(staffs);
        dynamic.setAttribute("id", String.valueOf(id));

        if(isGenerateElement()) dynamic.setAttribute("extension_line_to", idEvents.get(rand.nextInt(idEvents.size())));
        if(isGenerateElement()) dynamic.setAttribute("staff_ref", idStaffs.get(rand.nextInt(idStaffs.size())));
        dynamic.setAttribute("event_ref", idEvents.get(rand.nextInt(idEvents.size())));
        if(isGenerateElement()) dynamic.setAttribute("extension_line_shape", extension_line_shape[rand.nextInt(extension_line_shape.length)]);
        return dynamic;
    }

    private Element generate_random_duration(Document doc){
        Random rand = new Random();
        Element duration = doc.createElement("duration");
        duration.setAttribute("num", String.valueOf(rand.nextInt(5)));
        duration.setAttribute("den", String.valueOf(rand.nextInt(5)));
        if(isGenerateElement()){
            Element tuple_ratio = doc.createElement("tuple_ratio");
            tuple_ratio.setAttribute("enter_num", String.valueOf(rand.nextInt(5)));
            tuple_ratio.setAttribute("enter_den", String.valueOf(rand.nextInt(8)));
            tuple_ratio.setAttribute("in_num", String.valueOf(rand.nextInt(5)));
            tuple_ratio.setAttribute("in_den", String.valueOf(rand.nextInt(5)));
            if(isGenerateElement()) tuple_ratio.setAttribute("in_dots", String.valueOf(rand.nextInt(5)));
            duration.appendChild(tuple_ratio);
        }
        return duration;
    }

    private Element generate_random_description(Document doc){
        return doc.createElement("description");
    }

    private Element generate_random_date(Document doc){
        Element date = doc.createElement("date");
        date.setAttribute("type", "type_" + new Random().nextInt(30));
        return date;
    }

    private Element generate_random_custom_hsymbol(Document doc, int id, NodeList events){
        Element custom_hsymbol = doc.createElement("custom_hsymbol");
        Random rand = new Random();
        ArrayList<String> idEvents = getIds(events);
        if(isGenerateElement()) custom_hsymbol.setAttribute("id", String.valueOf(id));
        custom_hsymbol.setAttribute("start_event_ref", idEvents.get(rand.nextInt(idEvents.size())));
        custom_hsymbol.setAttribute("end_event_ref", idEvents.get(rand.nextInt(idEvents.size())));
        return custom_hsymbol;
    }

    private Element generate_random_csound_spine_ref(Document doc, NodeList events){
        Random rand = new Random();
        Element csound_spine_ref = doc.createElement("csound_spine_ref");

        ArrayList<String> idEvents = getIds(events);
        csound_spine_ref.setAttribute("start_event_ref", idEvents.get(rand.nextInt(idEvents.size())));
        csound_spine_ref.setAttribute("end_event_ref", idEvents.get(rand.nextInt(idEvents.size())));
        return  csound_spine_ref;
    }

    private Element generate_random_csound_spine_event(Document doc, NodeList events){
        Random rand = new Random();
        Element csound_spine_event = doc.createElement("csound_spine_event");

        ArrayList<String> idEvents = getIds(events);
        csound_spine_event.setAttribute("start_event_ref", idEvents.get(rand.nextInt(idEvents.size())));
        csound_spine_event.setAttribute("end_event_ref", idEvents.get(rand.nextInt(idEvents.size())));
        csound_spine_event.setAttribute("line", String.valueOf(rand.nextInt(40)));
        return  csound_spine_event;
    }

    private Element generate_random_csound_score(Document doc){
        Element csound_instance = doc.createElement("csound_score");
        return csound_instance;
    }

    private Element generate_random_csound_part_ref(Document doc, NodeList parts){
        Element csound_part_ref = doc.createElement("csound_part_ref");
        ArrayList<String> idsParts = getIds(parts);
        csound_part_ref.setAttribute("part_ref", idsParts.get(new Random().nextInt(idsParts.size())));
        return csound_part_ref;
    }

    private Element generate_random_csound_orchestra(Document doc){
        Element csound_orchestra = doc.createElement("csound_orchestra");
        csound_orchestra.setAttribute("file_name", "file_" + new Random().nextInt(50));
        return csound_orchestra;
    }

    private Element generate_random_csound_instrument_mapping(Document doc){
        Random rand = new Random();
        Element csound_instrument_mapping = doc.createElement("csound_instrument_mapping");
        csound_instrument_mapping.setAttribute("instrument_number", String.valueOf(rand.nextInt(20)));
        if(isGenerateElement()) csound_instrument_mapping.setAttribute("start_line", String.valueOf(rand.nextInt(50)));
        if(isGenerateElement()) csound_instrument_mapping.setAttribute("end_line", String.valueOf(rand.nextInt(50)));
        if(isGenerateElement()) csound_instrument_mapping.setAttribute("pnml_line", "file_" + String.valueOf(rand.nextInt(50)));
        return csound_instrument_mapping;

    }
    private Element generate_random_csound_instance(Document doc){
        return doc.createElement("csound_instance");
    }

    private Element generate_random_fine(Document doc, int id, NodeList events){
        Element fine = doc.createElement("fine");
        Random rand = new Random();
        ArrayList<String> idEvents = getIds(events);
        fine.setAttribute("start_event_ref", idEvents.get(rand.nextInt(idEvents.size())));
        fine.setAttribute("end_event_ref", idEvents.get(rand.nextInt(idEvents.size())));
        if(isGenerateElement()) fine.setAttribute("id", String.valueOf(id));
        return fine;
    }

    private Element generate_random_segno(Document doc, int id, NodeList events){
        Element segno = doc.createElement("segno");
        Random rand = new Random();
        ArrayList<String> idEvents = getIds(events);
        segno.setAttribute("start_event_ref", idEvents.get(rand.nextInt(idEvents.size())));
        segno.setAttribute("end_event_ref", idEvents.get(rand.nextInt(idEvents.size())));
        if(isGenerateElement()) segno.setAttribute("id", String.valueOf(id));
        return segno;
    }

    private Element generate_random_coda(Document doc, int id, NodeList events){
        Element coda = doc.createElement("coda");
        Random rand = new Random();
        ArrayList<String> idEvents = getIds(events);
        coda.setAttribute("start_event_ref", idEvents.get(rand.nextInt(idEvents.size())));
        coda.setAttribute("end_event_ref", idEvents.get(rand.nextInt(idEvents.size())));
        if(isGenerateElement()) coda.setAttribute("id", String.valueOf(id));
        return coda;
    }

    private Element generate_random_clef(Document doc, NodeList events){
        Element clef = doc.createElement("clef");
        Random rand = new Random();
        ArrayList<String> idEvents = getIds(events);
        clef.setAttribute("start_event_ref", idEvents.get(rand.nextInt(idEvents.size())));
        clef.setAttribute("end_event_ref", idEvents.get(rand.nextInt(idEvents.size())));
        clef.setAttribute("staff_step", String.valueOf(rand.nextInt(20)));
        String[] shape = {"G", "F", "C", "gregorian_F", "gregorian_C", "percussion", "doubleG", "tabguitar"};
        String[] octave_num = {"0", "8", "-8", "15"};
        clef.setAttribute("shape", shape[rand.nextInt(shape.length)]);
        clef.setAttribute("octave_num", octave_num[rand.nextInt(octave_num.length)]);
        return clef;
    }

    private Element generate_random_chord(Document doc, int id, NodeList events){
        String[] stem_direction = {"up", "down", "none"};
        String[] beam_and_cue = {"yes", "no"};
        String[] tremolo_lines = {"no", "1", "2", "3", "4", "5", "6"};
        ArrayList<String> idEvents = getIds(events);
        Random rand = new Random();
        Element chord = doc.createElement("chord");
        chord.setAttribute("id", String.valueOf(id));
        chord.setAttribute("event_ref", idEvents.get(rand.nextInt(idEvents.size())));
        if(isGenerateElement())  chord.setAttribute("stem_direction", stem_direction[rand.nextInt(stem_direction.length)]);
        if(isGenerateElement()) chord.setAttribute("beam_before", beam_and_cue[rand.nextInt(beam_and_cue.length)]);
        if(isGenerateElement()) chord.setAttribute("beam_before", beam_and_cue[rand.nextInt(beam_and_cue.length)]);
        chord.setAttribute("tremolo_lines", tremolo_lines[rand.nextInt(tremolo_lines.length)]);
        return chord;
    }

    private Element generate_random_chord_symbol(Document doc, int id, NodeList events){
        Random rand = new Random();
        Element chord_symbol = doc.createElement("chord_symbol");
        if(isGenerateElement()) chord_symbol.setAttribute("id", String.valueOf(id));
        ArrayList<String> idEvents = getIds(events);
        chord_symbol.setAttribute("start_event_ref", idEvents.get(rand.nextInt(idEvents.size())));
        chord_symbol.setAttribute("end_event_ref", idEvents.get(rand.nextInt(idEvents.size())));
        return chord_symbol;
    }

    private Element generate_random_chord_grid(Document doc, int id){
        Random rand = new Random();
        Element chord_grid = doc.createElement("chord_grid");
        if(isGenerateElement()) chord_grid.setAttribute("id", String.valueOf(id));
        if(isGenerateElement()) chord_grid.setAttribute("author", "author_" + rand.nextInt(20));
        if(isGenerateElement()) chord_grid.setAttribute("description", "description_" + rand.nextInt(20));
        int number_chord_name = rand.nextInt(10);
        for(int i = 0; i < number_chord_name; i++){
            Element chord_name = doc.createElement("chord_name");
            chord_name.setAttribute("root_id", String.valueOf(i));
            chord_grid.appendChild(chord_name);
        }
        return chord_grid;
    }

    private Element generate_random_breath_mark(Document doc, int id, NodeList events, NodeList staffs){
        Random rand = new Random();
        ArrayList<String> idEvents = getIds(events);
        ArrayList<String> idStaffs = getIds(staffs);
        Element breath_mark = doc.createElement("breath_mark");
        breath_mark.setAttribute("id", String.valueOf(id));

        if(isGenerateElement()) breath_mark.setAttribute("staff_ref", idStaffs.get(rand.nextInt(idStaffs.size())));
        breath_mark.setAttribute("start_event_ref", idEvents.get(rand.nextInt(idEvents.size())));
        breath_mark.setAttribute("end_event_ref", idEvents.get(rand.nextInt(idEvents.size())));
        String[] type = {"comma", "caesura"};
        breath_mark.setAttribute("type", type[rand.nextInt(type.length)]);
        return breath_mark;
    }

    private Element generate_random_brackets(Document doc){
        Random rand = new Random();
        Element brackets = doc.createElement("brackets");
        String[] marker = {"start_of_staff_group", "end_of_staff_group"};
        String[] shape = {"square", "rounded_square", "brace", "vertical_bar", "none"};
        brackets.setAttribute("marker", marker[rand.nextInt(marker.length)]);
        brackets.setAttribute("group_number", "group_" + rand.nextInt(20));
        brackets.setAttribute("shape", shape[rand.nextInt(shape.length)]);
        return brackets;
    }

    private Element generate_random_bend(Document doc, int id, NodeList events){
        Element bend = doc.createElement("bend");
        String[] type = {"single", "double"};
        String[] pitch = {"A", "B", "C", "D", "E", "F", "up", "down"};
        String[] accidental = {"none", "double_flat", "flat_and_a_half", "flat", "demiflat", "natural", "demisharp", "sharp", "sharp_and_a_half", "double_sharp"};
        Random rand = new Random();
        if(isGenerateElement()) bend.setAttribute("id", String.valueOf(id));
        ArrayList<String> idEvents = getIds(events);
        bend.setAttribute("event_ref", idEvents.get(rand.nextInt(idEvents.size())));
        bend.setAttribute("to_pitch", pitch[rand.nextInt(pitch.length)]);
        bend.setAttribute("type", type[rand.nextInt(type.length)]);
        if(isGenerateElement()) bend.setAttribute("to_accindental", accidental[rand.nextInt(accidental.length)]);
        if(isGenerateElement()) bend.setAttribute("to_octave", String.valueOf(rand.nextInt(12)));
        return bend;
    }

    private Element generate_random_barre(Document doc){
        Random rand = new Random();
        Element barre = doc.createElement("barre");
        barre.setAttribute("start_string_position", "start_string_" + rand.nextInt(20));
        barre.setAttribute("end_string_position", "end_string_" + rand.nextInt(20));
        barre.setAttribute("freat_position", "freat_string_" + rand.nextInt(20));
        return barre;
    }

    private Element generate_random_baroque_appoggiatura(Document doc, int id, NodeList events){
        Element baroque_appoggiatura = doc.createElement("baroque_appoggiatura");
        Random rand = new Random();
        String[] style = {"hairpin", "plus", "pipe", "double_slur", "slash", "backslash", "up_hook", "down_hook"};
        ArrayList<String> idEvents = getIds(events);
        baroque_appoggiatura.setAttribute("event_ref", idEvents.get(rand.nextInt(idEvents.size())));
        if(isGenerateElement()) baroque_appoggiatura.setAttribute("id", String.valueOf(id));
        baroque_appoggiatura.setAttribute("style", style[rand.nextInt(style.length)]);
        return baroque_appoggiatura;
    }

    private Element generate_random_baroque_acciaccatura(Document doc, int id, NodeList events){
        Element baroque_acconciatura = doc.createElement("baroque_acconciatura");
        Random rand = new Random();
        String[] style = {"vertical_turn", "mordent", "flatte", "tierce_coulee", "slash", "backslash"};
        ArrayList<String> idEvents = getIds(events);
        baroque_acconciatura.setAttribute("event_ref", idEvents.get(rand.nextInt(idEvents.size())));
        if(isGenerateElement()) baroque_acconciatura.setAttribute("id", String.valueOf(id));
        baroque_acconciatura.setAttribute("style", style[rand.nextInt(style.length)]);
        return baroque_acconciatura;

    }
    private Element generate_random_barline(Document doc, NodeList events){
        Element barline = doc.createElement("barline");
        Random rand = new Random();
        String[] style = {"dashed", "double", "final", "invisible", "standard", "smaller", "minimum"};
        String[] extension = {"single_staff", "staff_group", "all_staves", "mensurstrich"};
        ArrayList<String> idEvents = getIds(events);
        barline.setAttribute("event_ref", idEvents.get(rand.nextInt(idEvents.size())));
        barline.setAttribute("style", style[rand.nextInt(style.length)]);
        barline.setAttribute("extension", style[rand.nextInt(extension.length)]);
        return barline;
    }

    private Element generate_random_augmentation_dots(Document doc){
        Element augmentation_dots = doc.createElement("augmentation_dots");
        augmentation_dots.setAttribute("number", String.valueOf(new Random().nextInt(100)));
        return augmentation_dots;
    }

    private Element generate_random_audio(Document doc){
        Element audio = doc.createElement("audio");
        Element track = doc.createElement("track");
        int number_track = new Random().nextInt(10);
        for(int i = 0; i < number_track; i++){
            audio.appendChild(track);
        }
        return audio;
    }

    private Element generate_random_articulation(Document doc){
        Element articulation = doc.createElement("articulation");
        Random rand = new Random();
        Element[] elements = {doc.createElement("custom_articulation"), doc.createElement("normal_accent"),
                doc.createElement("staccato"), doc.createElement("staccatissimo"), doc.createElement("strong_accent"),
                doc.createElement("tenuto"), doc.createElement("stopped_note"), doc.createElement("snap_pizzicato"),
                doc.createElement("natural_armonic"), doc.createElement("up_bow"), doc.createElement("down_bow"),
                doc.createElement("open_mute"), doc.createElement("close_mute")};

        Element e = elements[rand.nextInt(elements.length)];
        if(!e.getTagName().equals("custom_articulation")) articulation.appendChild(e);
        else{
            int number_of_articulation = rand.nextInt(20);
            for(int j = 0; j < number_of_articulation; j++) articulation.appendChild(e);
        }
        return articulation;

    }

    private Element generate_random_arpeggio(Document doc){
        Element arpeggio = doc.createElement("arpeggio");
        Random rand = new Random();
        String[] shape = {"wavy", "line", "no_arpeggio"};
        String[] direction = {"up", "down"};
        arpeggio.setAttribute("shape", shape[rand.nextInt(shape.length)]);
        arpeggio.setAttribute("direction", direction[rand.nextInt(direction.length)]);
        return arpeggio;
    }

    private Element generate_random_notehead_ref(Document doc, NodeList events){
        Random rand = new Random();
        Element notehead_ref = doc.createElement("notehead_ref");
        ArrayList<String> idEvents = getIds(events);
        notehead_ref.setAttribute("event_ref", idEvents.get(rand.nextInt(idEvents.size())));
        return notehead_ref;
    }

    private Element generate_random_appoggiatura(Document doc, int id, NodeList events){
        Element appoggiatura = doc.createElement("appoggiatura");
        String[] slur = {"yes", "no"};
        Random rand = new Random();
        Element analysis = doc.createElement("appoggiatura");
        if(isGenerateElement()) analysis.setAttribute("id", String.valueOf(id));
        appoggiatura.setAttribute("slur", slur[rand.nextInt(slur.length)]);
        ArrayList<String> idEvents = getIds(events);
        appoggiatura.setAttribute("event_ref", idEvents.get(rand.nextInt(idEvents.size())));
        return appoggiatura;
    }

    private Element generate_random_analysis(Document doc, int id){
        Random rand = new Random();
        Element analysis = doc.createElement("doc");
        if(isGenerateElement()) analysis.setAttribute("id", String.valueOf(id));
        if(isGenerateElement()) analysis.setAttribute("author", "author_" + rand.nextInt(100));
        if(isGenerateElement()) analysis.setAttribute("description", "description_" + rand.nextInt(100));
        return analysis;
    }

    private Element generate_random_analog_media(Document doc){
        Element analog_media = doc.createElement("analog_media");
        return analog_media;
    }


    private Element generate_random_analog_medium(Document doc){
        Random rand = new Random();
        Element analog_medium = doc.createElement("analog_medium");
        analog_medium.setAttribute("description", "description_" + rand.nextInt(200));
        if(isGenerateElement()) analog_medium.setAttribute("copyright", "copyright_" + rand.nextInt(200));
        if(isGenerateElement()) analog_medium.setAttribute("notes", "notes_" + rand.nextInt(200));
        return analog_medium;
    }


    private Element generate_random_album(Document doc){
        Random rand = new Random();
        Element album = doc.createElement("album");
        album.setAttribute("title", "album_" + rand.nextInt(100));
        if(isGenerateElement()) album.setAttribute("carrier_type", "carrier_" + rand.nextInt(200));
        if(isGenerateElement()) album.setAttribute("catalogue_number", String.valueOf(rand.nextInt(200)));
        if(isGenerateElement()) album.setAttribute("number_of_tracks", String.valueOf(rand.nextInt(200)));
        if(isGenerateElement()) album.setAttribute("pubblication_date", generate_date());
        if(isGenerateElement()) album.setAttribute("label", "label_" + rand.nextInt(200));
        return album;
    }

    private Element generate_random_albums(Document doc){
        Element albums = doc.createElement("albums");
        return albums;
    }

    private Element generate_random_agogics(Document doc, int id, NodeList events){
        Random rand = new Random();
        Element agogics = doc.createElement("agogics");
        String[] bracketed = {"yes", "no"};
        if(isGenerateElement()) agogics.setAttribute("bracketed", bracketed[rand.nextInt(bracketed.length)]);
        ArrayList<String> idEvents = getIds(events);
        agogics.setAttribute("event_ref", idEvents.get(rand.nextInt(idEvents.size())));
        return agogics;
    }

    private Element generate_random_acciaccatura(Document doc, int id, NodeList events){
        Random rand = new Random();
        String[] slur = {"yes", "no"};
        Element acciaccatura = doc.createElement("acciaccatura");

        if(isGenerateElement()) acciaccatura.setAttribute("id", String.valueOf(id));

        ArrayList<String> idEvents = getIds(events);

        acciaccatura.setAttribute("event_ref", idEvents.get(rand.nextInt(idEvents.size())));
        acciaccatura.setAttribute("slur", slur[rand.nextInt(slur.length)]);

        return acciaccatura;
    }


    // vedere SaveMelody.java nel file xml.pdf
    // <pitch octave="6" step="D" actual_accidental="natural" />
    private Element generate_random_pitch(Document doc){
        Random rand = new Random();
        ArrayList<String> notes = configuration.getNotes();
        int[] max_min_height = configuration.getMin_max_height();
        ArrayList<String> accidentals = configuration.getAccidentals();
        int[] max_min_numerator = configuration.getMin_max_numerator();
        int[] max_min_denominator = configuration.getMin_max_denominator();
        Note n = new Note();
        n.accidental = AccidentalEnum.valueOf(accidentals.get(rand.nextInt(accidentals.size())));
        n.octave = rand.nextInt(max_min_height[1] - max_min_height[0]) + max_min_height[0];
        n.pitch = PitchEnum.valueOf(notes.get(rand.nextInt(notes.size())));
        n.numerator = rand.nextInt(max_min_numerator[1] - max_min_numerator[0]) + max_min_numerator[0];
        n.denominator = rand.nextInt(max_min_denominator[1] - max_min_denominator[0]) + max_min_denominator[0];
        Element pitch = doc.createElement("pitch");
        pitch.setAttribute("actual_accidental", ((AccidentalEnum)(n.accidental)).name().toLowerCase());
        pitch.setAttribute("step", ((PitchEnum)(n.pitch)).name());
        pitch.setAttribute("octave", String.valueOf(n.octave));
        return pitch;
    }



    private ArrayList<String> getIds(NodeList elements){
        Random rand = new Random();
        ArrayList<String> idElements = new ArrayList<>();
        for(int i = 0; i < elements.getLength(); i++){
            Element e = (Element) (elements.item(i));
            idElements.add(e.getAttribute("id"));
        }
        return idElements;
    }






    private void save_xml_file(Document doc, String description){
        // Salvataggio
        try {
            if(description.equals("new")) {
                Element rootElement = doc.createElement("ieee1599");
                rootElement.setAttribute("version", "1.0");
                doc.appendChild(rootElement);
            }
            Result output = new StreamResult(new File(this.path + this.nameOfFile));
            Source input = new DOMSource(doc);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "3");
            t.transform(input, output);
        } catch (TransformerException e) {
            System.out.println("Errore durante il salvataggio...");
        }
    }

}
