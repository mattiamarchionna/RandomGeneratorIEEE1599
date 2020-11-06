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
    private Random r;



    public GeneratorIEEE1599(String path, String nameOfFile, Parameter configuration){
        this.path = path;
        this.nameOfFile = nameOfFile;
        this.configuration = configuration;
        r = new Random();
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
        
        return r.nextBoolean();
    }

    private String generate_date() {
        Random random = new Random();
        int minDay = (int) LocalDate.of(1900, 1, 1).toEpochDay();
        int maxDay = (int) LocalDate.of(2018, 1, 1).toEpochDay();
        long randomDay = minDay + random.nextInt(maxDay - minDay);
        LocalDate randomBirthDate = LocalDate.ofEpochDay(randomDay);
        return randomBirthDate.toString();
    }

    private Element generate_random_rest(Document doc, int id, NodeList events, NodeList staffs){
        Element rest = doc.createElement("rest");
        ArrayList<String> idsStaff = getIds(staffs);
        ArrayList<String> idsEvents = getIds(events);
        String[] hidden = {"yes", "no"};
        if(isGenerateElement()) rest.setAttribute("id", String.valueOf(id));
        if(isGenerateElement()) rest.setAttribute("staff_ref", idsStaff.get(r.nextInt(idsStaff.size())));
        if(isGenerateElement()) rest.setAttribute("event_ref", idsEvents.get(r.nextInt(idsEvents.size())));
        if(isGenerateElement()) rest.setAttribute("hidden", hidden[r.nextInt(hidden.length)]);
        return rest;
    }

    private Element generate_random_repetition(Document doc){
        return doc.createElement("repetition");
    }

    private Element generate_random_repeat_text(Document doc){
        return doc.createElement("repeat_text");
    }

    private Element generate_random_repeat(Document doc, int id, NodeList events){
        Element repeat = doc.createElement("repeat");
        if(isGenerateElement()) repeat.setAttribute("id", String.valueOf(id));
        ArrayList<String> idEvents = getIds(events);
        repeat.setAttribute("event_ref", idEvents.get(r.nextInt(idEvents.size())));
        return repeat;
    }

    private Element generate_random_relationships(Document doc, NodeList segments, NodeList feature_objects, NodeList feature_object_relationship){
        Element relationships = doc.createElement("relationships");

        int number_relationship = r.nextInt(10);

        for(int i = 0; i < number_relationship; i++) {
            Element relationship = doc.createElement("relationship");
            relationship.setAttribute("id", String.valueOf(i));
            ArrayList<String> segment_refs = getIds(segments);
            ArrayList<String> feature_object_refs = getIds(feature_objects);
            ArrayList<String> feature_object_relationship_ref = getIds(feature_object_relationship);

            if (isGenerateElement()) relationship.setAttribute("description", "description_" + r.nextInt(20));
            relationship.setAttribute("segment_a_ref", segment_refs.get(r.nextInt(segment_refs.size())));
            relationship.setAttribute("segment_b_ref", segment_refs.get(r.nextInt(segment_refs.size())));
            if (isGenerateElement()) relationship.setAttribute("feature_object_a_ref", feature_object_refs.get(r.nextInt(feature_object_refs.size())));
            if (isGenerateElement()) relationship.setAttribute("feature_object_b_ref", feature_object_refs.get(r.nextInt(feature_object_refs.size())));
            if (isGenerateElement()) relationship.setAttribute("feature_object_relationship_ref", feature_object_relationship_ref.get(r.nextInt(feature_object_relationship_ref.size())));
            relationships.appendChild(relationship);
        }
        return relationships;
    }

    private Element generate_random_feature_object_relationships(Document doc){

        Element feature_object_relationships = doc.createElement("feature_object_relationships");

        int number_relationship = r.nextInt(10);
        for(int i = 0; i < number_relationship; i++){
            Element feature_object_relationship = doc.createElement("feature_object_relationship");
            feature_object_relationship.setAttribute("id", String.valueOf(i));
            Element ver_rule = doc.createElement("ver_rule");
            feature_object_relationship.appendChild(ver_rule);
            feature_object_relationships.appendChild(feature_object_relationship);
        }
        return feature_object_relationships;
    }


    private Element generate_random_related_files(Document doc, NodeList events){
        Element related_files = doc.createElement("related_files");
        int number_related_file = r.nextInt(10);
        for(int i = 0; i < number_related_file; i++) {
            Element related_file = doc.createElement("related_file");
            ArrayList<String> idEvents = getIds(events);
            related_file.setAttribute("start_event_ref", idEvents.get(r.nextInt(idEvents.size())));
            if (isGenerateElement()) related_file.setAttribute("end_event_ref", idEvents.get(r.nextInt(idEvents.size())));
            if(isGenerateElement()) related_file.setAttribute("description", "description_" + i);
            if(isGenerateElement()) related_file.setAttribute("notes", "notes_" + i);
            if(isGenerateElement()) related_file.setAttribute("copyright", "copyright_" + i);
            related_file.setAttribute("file_name", "file_" + i);
            related_file.setAttribute("file_format", formats[r.nextInt(formats.length)]);
            related_file.setAttribute("encoding_format", formats[r.nextInt(formats.length)]);
            related_files.appendChild(related_file);
        }
        return related_files;
    }

    private Element generate_random_recordings(Document doc){
        return doc.createElement("recordings");
    }

    private Element generate_random_recording(Document doc){
        Element recording = doc.createElement("recording");
        recording.setAttribute("date", generate_date());
        if(isGenerateElement()) recording.setAttribute("recorded_part", "part_" + r.nextInt(100));
        if(isGenerateElement()) recording.setAttribute("studio_name", "studio_" + r.nextInt(100));
        if(isGenerateElement()) recording.setAttribute("studio_address", "address_" + r.nextInt(100));
        return recording;
    }

    private Element generate_random_printed_accidentals(Document doc){
        Element printed_accidentals = doc.createElement("printed_accidentals");
        String[] shape = {"normal", "small", "bracketed"};
        printed_accidentals.setAttribute("shape", shape[r.nextInt(shape.length)]);
        return printed_accidentals;
    }

    private Element generate_random_place(Document doc, NodeList segments){
        Element place = doc.createElement("place");
        ArrayList<String> idSegments = getIds(segments);
        place.setAttribute("segment_ref", idSegments.get(r.nextInt(idSegments.size())));
        place.setAttribute("place_ref", "id_" + r.nextInt(100));
        return place;
    }

    private Element generate_random_petri_nets(Document doc){
        return doc.createElement("petri_nets");
    }

    private Element generate_random_petri_net(Document doc){
        Element petri_net = doc.createElement("petri_net");
        petri_net.setAttribute("file_name", "file_" + r.nextInt(100));
        return petri_net;
    }


    private Element generate_random_performers(Document doc){
        Element performers = doc.createElement("performers");
        int number_perfomer = r.nextInt(10);
        for(int i = 0; i < number_perfomer; i++){
            Element performer = doc.createElement("performer");
            performer.setAttribute("name", "name_" + i);
            performer.setAttribute("type", "type_" + i);
            performers.appendChild(performer);
        }
        return performers;
    }

    private Element generate_random_performance(Document doc){
        return doc.createElement("performance");
    }

    private Element generate_random_percussion_special(Document doc, int id, NodeList events){
        String[] type = ("play_on_border,stop_drumhead,muffle_with_harmonics,muffle,rub,shake").split(",");
        Element percussion_special = doc.createElement("percussion_special");
        if(isGenerateElement()) percussion_special.setAttribute("id", String.valueOf(id));
        percussion_special.setAttribute("type", type[r.nextInt(type.length)]);
        ArrayList<String> idEvents = getIds(events);
        if(isGenerateElement()) percussion_special.setAttribute("event_ref", idEvents.get(r.nextInt(idEvents.size())));
        return percussion_special;
    }

    private Element generate_random_percussion_beater(Document doc, int id, NodeList events){
        String[] type =  ("bow,snare_stick,snare_stick_plastic,spoon_shaped,guiro,triangle,knitting_needle,hand,hammer,metal_hammer,wooden_timpani_mallet,light_timpani_mallet,medium_timpani_mallet,heavy_timpani_mallet,light_vibraphone_mallet,medium_vibraphone_mallet,heavy_vibraphone_mallet,light_bassdrum_mallet,medium_bassdrum_mallet,heavy_bassdrum_mallet,steel_core_bassdrum_mallet,coin,brush,nails").split(",");
        Element percussion_beater = doc.createElement("percussion_beater");
        if(isGenerateElement()) percussion_beater.setAttribute("id", String.valueOf(id));
        ArrayList<String> idEvents = getIds(events);
        percussion_beater.setAttribute("start_event_ref", idEvents.get(r.nextInt(idEvents.size())));
        if(isGenerateElement()) percussion_beater.setAttribute("end_event_ref", idEvents.get(r.nextInt(idEvents.size())));
        percussion_beater.setAttribute("type", type[r.nextInt(type.length)]);
        return percussion_beater;
    }

    private Element generate_random_pedal_end(Document doc, int id, NodeList events){
        Element pedal_end = doc.createElement("pedal_end");
        if(isGenerateElement()) pedal_end.setAttribute("id", String.valueOf(id));
        ArrayList<String> idEvents = getIds(events);
        pedal_end.setAttribute("event_ref", idEvents.get(r.nextInt(idEvents.size())));
        return pedal_end;
    }

    private Element generate_random_pedal_start(Document doc, int id, NodeList events){
        Element pedal_start = doc.createElement("pedal_start");
        if(isGenerateElement()) pedal_start.setAttribute("id", String.valueOf(id));
        ArrayList<String> idEvents = getIds(events);
        pedal_start.setAttribute("event_ref", idEvents.get(r.nextInt(idEvents.size())));
        return pedal_start;
    }

    private Element generate_random_part(Document doc, int id){
        String[] pitch = {"A", "B", "C", "D", "E", "F", "G"};
        String[] accidental = {"none", "double_flat", "flat_and_a_half", "flat", "demiflat", "natural", "demisharp", "sharp", "sharp_and_a_half", "double_sharp"};
        Element part = doc.createElement("part");
        part.setAttribute("id", String.valueOf(id));
        if(isGenerateElement()) part.setAttribute("transposition_pitch", pitch[r.nextInt(pitch.length)]);
        if(isGenerateElement()) part.setAttribute("transposition_accidental", accidental[r.nextInt(accidental.length)]);
        if(isGenerateElement()) part.setAttribute("performers_number", "unknown");
        else part.setAttribute("performers_number", String.valueOf(r.nextInt(20)));
        part.setAttribute("octave_offset", String.valueOf(r.nextInt(9 + 8) - 8)); // da -8 ad 8
        return part;
    }

    private Element generate_random_page(Document doc, int id){
        Element page = doc.createElement("page");
        page.setAttribute("id", String.valueOf(id));
        if(isGenerateElement()) page.setAttribute("number", String.valueOf(r.nextInt(100)));
        return page;
    }

    private Element generate_random_other_title(Document doc){
        return doc.createElement("other_title");
    }

    private Element generate_random_ornaments(Document doc){
        return doc.createElement("ornaments");
    }

    private Element generate_random_octave_bracket(Document doc, int id, NodeList staffs, NodeList events){
        Element octave_brackets = doc.createElement("octave_brackets");
        String[] type = {"8va", "8vb", "15ma", "15mb"};
        ArrayList<String> idStaffs = getIds(staffs);
        ArrayList<String> idEvents = getIds(events);
        if(isGenerateElement()) octave_brackets.setAttribute("id", String.valueOf(id));
        if(isGenerateElement()) octave_brackets.setAttribute("style", type[r.nextInt(idStaffs.size())]);
        if(isGenerateElement()) octave_brackets.setAttribute("staff_ref", idStaffs.get(r.nextInt(idStaffs.size())));
        octave_brackets.setAttribute("type", type[r.nextInt(type.length)]);
        octave_brackets.setAttribute("start_event_ref", idEvents.get(r.nextInt(idEvents.size())));
        octave_brackets.setAttribute("end_event_ref", idEvents.get(r.nextInt(idEvents.size())));
        return octave_brackets;
    }
    
    private Element generate_random_number(Document doc){
        return doc.createElement("number");
    }

    private Element generate_random_notes(Document doc){
        return doc.createElement("notes");
    }

    private Element generate_random_notehead(Document doc, int id, NodeList staffs){
        Element notehead = doc.createElement("notehead");
        
        String[] style = {"normal", "harmonic", "unpitched", "cymbal", "parenthesis", "circled", "squared"};
        ArrayList<String> idStaffs = getIds(staffs);
        if(isGenerateElement()) notehead.setAttribute("id", String.valueOf(id));
        if(isGenerateElement()) notehead.setAttribute("style", style[r.nextInt(idStaffs.size())]);
        if(isGenerateElement()) notehead.setAttribute("staff_ref", idStaffs.get(r.nextInt(idStaffs.size())));
        return notehead;
    }

    private Element generate_random_notational(Document doc){
        return doc.createElement("notational");
    }

    private Element generate_random_natation_instance_group(Document doc){
        Element notation_instance_group = doc.createElement("notation_instance_group");
        
        notation_instance_group.setAttribute("description", "description_" + r.nextInt(50));
        return notation_instance_group;
    }

    private Element generate_random_notation_instance(Document doc, NodeList events){
        Element notation_instance = doc.createElement("notation_instance");
        String[] measurement_unit = {"centimeters", "millimeters", "inches", "decimal_inches", "points", "picas", "pixels", "twips"};
        
        notation_instance.setAttribute("description", "description_" + r.nextInt(50));
        notation_instance.setAttribute("format", formats[r.nextInt(formats.length)]);
        notation_instance.setAttribute("measurement_unit", measurement_unit[r.nextInt(measurement_unit.length)]);
        notation_instance.setAttribute("file_name", "file_" + r.nextInt(100));
        notation_instance.setAttribute("position_in_group", String.valueOf(r.nextInt(100)));
        return notation_instance;
    }

    private Element generate_random_notation_event(Document doc, NodeList events){
        Element notation_event = doc.createElement("notation_event");
        ArrayList<String> idEvents = getIds(events);
        
        notation_event.setAttribute("event_ref", idEvents.get(r.nextInt(idEvents.size())));
        notation_event.setAttribute("description", "description_" + r.nextInt(50));
        notation_event.setAttribute("start_position", String.valueOf(r.nextInt(50)));
        notation_event.setAttribute("end_position", String.valueOf(r.nextInt(50)));
        return notation_event;
    }

    private Element generate_random_articulation(Document doc){
        Element articulation = doc.createElement("articulation");
        
        Element[] elements = {doc.createElement("custom_articulation"), doc.createElement("normal_accent"),
                doc.createElement("staccato"), doc.createElement("staccatissimo"), doc.createElement("strong_accent"),
                doc.createElement("tenuto"), doc.createElement("stopped_note"), doc.createElement("snap_pizzicato"),
                doc.createElement("natural_armonic"), doc.createElement("up_bow"), doc.createElement("down_bow"),
                doc.createElement("open_mute"), doc.createElement("close_mute")};

        int number_of_articulation = r.nextInt(20);
        for(int j = 0; j < number_of_articulation; j++) articulation.appendChild(elements[r.nextInt(elements.length)]);
        return articulation;

    }

    private Element generate_random_music_font(Document doc){
        return doc.createElement("music_font");
    }

    private Element generate_random_multiple_rest(Document doc, NodeList events){
        ArrayList<String> idEvents = getIds(events);
        
        Element multiple_rest = doc.createElement("multiple_rest");
        if(isGenerateElement()) multiple_rest.setAttribute("event_ref", idEvents.get(r.nextInt(idEvents.size())));
        multiple_rest.setAttribute("number_of_measures", String.valueOf(r.nextInt(50)));
        return multiple_rest;
    }


    private Element generate_random_multiple_ending(Document doc, int id, NodeList events){
        Element multiple_endings = doc.createElement("multiple_endigs");
        if(isGenerateElement()) multiple_endings.setAttribute("id", String.valueOf(id));
        ArrayList<String> idEvents = getIds(events);
        
        int number_multiple_ending = r.nextInt(10);
        for(int i = 0; i < number_multiple_ending; i++) {
            Element multiple_ending = doc.createElement("multiple_ending");
            if(isGenerateElement()) multiple_ending.setAttribute("id", "id_" + id + "_" + i);
            multiple_ending.setAttribute("number", String.valueOf(r.nextInt(30)));
            multiple_ending.setAttribute("return_to", idEvents.get(r.nextInt(idEvents.size())));
            multiple_ending.setAttribute("start_event_ref", idEvents.get(r.nextInt(idEvents.size())));
            multiple_ending.setAttribute("end_event_ref", idEvents.get(r.nextInt(idEvents.size())));
            multiple_endings.appendChild(multiple_ending);
        }
        return multiple_endings;
    }

    private Element generate_random_mpeg4_spine_ref(Document doc, NodeList events){
        Element mpeg4_spine_ref = doc.createElement("mpeg4_spine_ref");
        ArrayList<String> idEvents = getIds(events);
        mpeg4_spine_ref.setAttribute("event_ref", idEvents.get(new Random().nextInt(idEvents.size())));
        return mpeg4_spine_ref;
    }


    private Element generate_random_mpeg4_spine_event(Document doc, NodeList events){
        Element mpeg4_spine_event = doc.createElement("mpeg4_spine_event");
        ArrayList<String> idEvents = getIds(events);
        mpeg4_spine_event.setAttribute("event_ref", idEvents.get(new Random().nextInt(idEvents.size())));
        mpeg4_spine_event.setAttribute("line_number", String.valueOf(new Random().nextInt(50)));
        return mpeg4_spine_event;
    }

    private Element generate_random_mpeg4score(Document doc){
        Element mpeg4score = doc.createElement("mpeg4score");
        
        mpeg4score.setAttribute("file_name", "file_" + r.nextInt(100));
        return mpeg4score;
    }

    private Element generate_random_mpeg4_orchestra(Document doc){
        
        Element mpeg4_orchestra = doc.createElement("mpeg4_orchestra");
        mpeg4_orchestra.setAttribute("file_name", "file_" + r.nextInt(100));
        return mpeg4_orchestra;
    }

    private Element generate_random_mpeg4_instrument_mapping(Document doc){
        
        Element mpeg4_instrument_mapping = doc.createElement("mpeg4_instrument_mapping");
        mpeg4_instrument_mapping.setAttribute("instrument_name", "instrument_" + r.nextInt(100));
        if(isGenerateElement()) mpeg4_instrument_mapping.setAttribute("start_line", String.valueOf(r.nextInt(50)));
        if(isGenerateElement()) mpeg4_instrument_mapping.setAttribute("end_line", String.valueOf(r.nextInt(50)));
        if(isGenerateElement()) mpeg4_instrument_mapping.setAttribute("pnml_file", "file_" + r.nextInt(50));
        return mpeg4_instrument_mapping;
    }

    private Element generate_random_mpeg4_part_ref(Document doc, NodeList parts){
        Element mpeg4_part_ref = doc.createElement("mpeg4_part_ref");
        ArrayList<String> idParts = getIds(parts);
        mpeg4_part_ref.setAttribute("part_ref", idParts.get(new Random().nextInt(idParts.size())));
        return mpeg4_part_ref;
    }

    private Element generate_random_mpeg4_instance(Document doc){
        return doc.createElement("mpeg4_instance");
    }

    private Element generate_random_mordent(Document doc, int id, NodeList events){
        String[] style = {"normal", "double-rhomb", "up_hook", "down_hook"};
        String[] type = {"upper", "lower"};
        String[] length = {"normal", "double"};
        String[] accidental = {"none", "double_flat", "flat_and_a_half", "flat", "demiflat", "natural", "demisharp", "sharp", "sharp_and_a_half", "double_sharp"};
        
        Element mordent = doc.createElement("mordent");
        ArrayList<String> idEvents = getIds(events);
        mordent.setAttribute("event_ref", idEvents.get(r.nextInt(idEvents.size())));
        mordent.setAttribute("type", type[r.nextInt(type.length)]);
        mordent.setAttribute("length", length[r.nextInt(length.length)]);
        mordent.setAttribute("accidental", accidental[r.nextInt(accidental.length)]);
        mordent.setAttribute("style", style[r.nextInt(style.length)]);
        return  mordent;
    }

    private Element generate_random_mir_subobject(Document doc, int id, NodeList segments){
        
        ArrayList<String> idSegments = getIds(segments);
        Element mir_subobject = doc.createElement("mir_subobject");
        if(isGenerateElement()) mir_subobject.setAttribute("id", String.valueOf(id));
        if(isGenerateElement()) mir_subobject.setAttribute("description", "description_" + r.nextInt(100));
        if(isGenerateElement()) mir_subobject.setAttribute("displacement_ref", "displacement_" + r.nextInt(100));
        if(isGenerateElement()) mir_subobject.setAttribute("segment_ref", idSegments.get(r.nextInt(idSegments.size())));
        return mir_subobject;
    }

    private Element generate_random_mir_object(Document doc, int id){
        
        Element mir_object = doc.createElement("mir_object");
        if(isGenerateElement()) mir_object.setAttribute("id", String.valueOf(id));
        if(isGenerateElement()) mir_object.setAttribute("description", "description_" + r.nextInt(100));
        if(isGenerateElement()) mir_object.setAttribute("displacement_ref", "displacement_" + r.nextInt(100));
        return mir_object;
    }

    private Element generate_random_mir_morphism(Document doc, int id, NodeList domains, NodeList codomains){
        ArrayList<String> idDomains = getIds(domains);
        ArrayList<String> idCodomains = getIds(codomains);
        
        Element mir_morphism = doc.createElement("mir_morphism");
        if(isGenerateElement()) mir_morphism.setAttribute("id", String.valueOf(id));
        if(isGenerateElement()) mir_morphism.setAttribute("description", "description_" + r.nextInt(100));
        mir_morphism.setAttribute("domain_ref", idDomains.get(r.nextInt(idDomains.size())));
        mir_morphism.setAttribute("codomain_ref", idCodomains.get(r.nextInt(idCodomains.size())));
        if(isGenerateElement()) mir_morphism.setAttribute("displacement_ref", "displacement_" + r.nextInt(100));
        return mir_morphism;
    }

    private Element generate_random_mir_model(Document doc, int id){
        
        Element mir_model = doc.createElement("mir_model");
        if(isGenerateElement()) mir_model.setAttribute("id", String.valueOf(id));
        if(isGenerateElement()) mir_model.setAttribute("description", "description_" + r.nextInt(100));
        if(isGenerateElement()) mir_model.setAttribute("file_name", "file_" + r.nextInt(100));
        return mir_model;
    }

    private Element generate_random_mir_feature(Document doc, int id){
        
        Element mir_feature = doc.createElement("mir_feature");
        if(isGenerateElement()) mir_feature.setAttribute("id", String.valueOf(id));
        if(isGenerateElement()) mir_feature.setAttribute("description", "description_" + r.nextInt(100));
        if(isGenerateElement()) mir_feature.setAttribute("displacement_ref", "displacement_" + r.nextInt(100));
        return mir_feature;
    }

    private Element generate_random_mir(Document doc){
        return doc.createElement("mir");
    }

    private Element generate_random_midi_mapping(Document doc, NodeList voices, NodeList parts){
        
        ArrayList<String> idVoices = getIds(voices);
        ArrayList<String> idParts = getIds(parts);
        Element midi_mapping = doc.createElement("midi_event");
        midi_mapping.setAttribute("part_ref", idParts.get(r.nextInt(idParts.size())));
        if(isGenerateElement()) midi_mapping.setAttribute("voice_ref", idVoices.get(r.nextInt(idVoices.size())));
        midi_mapping.setAttribute("track", "track_" + r.nextInt(100));
        midi_mapping.setAttribute("channel", "channel_" + r.nextInt(100));
        return midi_mapping;
    }


    private Element generate_random_midi_instance(Document doc){
        Element midi_instance = doc.createElement("midi_instance");
        
        midi_instance.setAttribute("file_name", "file_" + r.nextInt(100));
        String[] format = {"0", "1", "2"};
        midi_instance.setAttribute("format", format[r.nextInt(format.length)]);
        return  midi_instance;
    }

    private Element generate_random_midi_event_sequence(Document doc, NodeList events) {
        Element midi_event_sequence = doc.createElement("midi_event_sequence");
        String[] type = {"metrical", "timecode"};
        String[] unit = {"ticks", "sec"};
        
        midi_event_sequence.setAttribute("division_value", String.valueOf(r.nextFloat()).substring(0, 5));
        midi_event_sequence.setAttribute("type", type[r.nextInt(type.length)]);
        midi_event_sequence.setAttribute("measurement_unit", unit[r.nextInt(unit.length)]);
        return midi_event_sequence;
    }

    private Element generate_random_midi_event(Document doc, NodeList events){
        Element midi_event = doc.createElement("midi_event");
        ArrayList<String> idsEvents = new ArrayList<>(getIds(events));
        
        midi_event.setAttribute("event_ref", idsEvents.get(r.nextInt(idsEvents.size())));
        midi_event.setAttribute("timing", "timing_" + r.nextInt(100));
        return midi_event;
    }

    private Element generate_random_metronomic_indication(Document doc, NodeList events){
        
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
        
        if (isGenerateElement()) {
            ArrayList<String> idEvents = getIds(events);
            measure_repeat.setAttribute("event_ref", idEvents.get(r.nextInt(idEvents.size())));
        }
        measure_repeat.setAttribute("number_of_measures", String.valueOf(r.nextInt(100)));
        return measure_repeat;
    }

    private Element generate_random_measure(Document doc, int id){
        Element measure = doc.createElement("measure");
        if(isGenerateElement()) measure.setAttribute("id", String.valueOf(id));
        
        measure.setAttribute("number", String.valueOf(r.nextInt(100)));
        String[] show = {"yes", "no"};
        String[] style = {"arabic_numbers", "roman_number", "small_letters", "capital_letters"};
        if(isGenerateElement()) measure.setAttribute("show_number", show[r.nextInt(show.length)]);
        if(isGenerateElement()) measure.setAttribute("numbering_style", style[r.nextInt(style.length)]);
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
        

        lyrics.setAttribute("part_ref", partIds.get(r.nextInt(partIds.size())));
        lyrics.setAttribute("voice_ref", voiceIds.get(r.nextInt(voiceIds.size())));
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
        
        Element layout = doc.createElement("layout");
        layout.setAttribute("hpos_per_unit", String.valueOf(r.nextInt(20)));
        layout.setAttribute("measurement_unit", measurement_unit[r.nextInt(measurement_unit.length)]);
        return layout;
    }

    private Element generate_random_layout_system(Document doc, int id){
        Element layout_system = doc.createElement("layout_system");
        if(isGenerateElement()) layout_system.setAttribute("id", String.valueOf(id));
        
        layout_system.setAttribute("upper_left_x", String.valueOf(r.nextInt(20)));
        layout_system.setAttribute("upper_left_y", String.valueOf(r.nextInt(20)));
        layout_system.setAttribute("lower_right_x", String.valueOf(r.nextInt(20)));
        layout_system.setAttribute("lower_right_x", String.valueOf(r.nextInt(20)));
        return layout_system;
    }

    private Element generate_random_layout_staff(Document doc, int id, NodeList staffs){
        Element layout_staff = doc.createElement("layout_staff");
        if(isGenerateElement()) layout_staff.setAttribute("id", String.valueOf(id));
        ArrayList<String> idStaffs = getIds(staffs);
        String[] values = {"yes", "no"};
        
        layout_staff.setAttribute("staff_ref", idStaffs.get(new Random().nextInt(idStaffs.size())));
        layout_staff.setAttribute("vertical_offset", String.valueOf(r.nextInt(20)));
        layout_staff.setAttribute("height", String.valueOf(r.nextInt(20)));
        layout_staff.setAttribute("ossia", values[r.nextInt(values.length)]);
        layout_staff.setAttribute("show_time_signature", values[r.nextInt(values.length)]);
        layout_staff.setAttribute("show_key_clef", values[r.nextInt(values.length)]);
        layout_staff.setAttribute("show_key_signature", values[r.nextInt(values.length)]);
        return layout_staff;
    }

    private Element generate_random_layout_shapes(Document doc) {
        Element layout_shapes = doc.createElement("layout_shapes");
        
        layout_shapes.setAttribute("horizontal_offset", String.valueOf(r.nextInt(20)));
        layout_shapes.setAttribute("vertical_offset", String.valueOf(r.nextInt(20)));
        return layout_shapes;
    }

    private Element generate_random_layout_images(Document doc) {
        Element layout_images = doc.createElement("layout_images");
        
        layout_images.setAttribute("file_name", "file_" + r.nextInt(50));
        layout_images.setAttribute("file_format", this.formats[r.nextInt(formats.length)]);
        layout_images.setAttribute("encoding_format", this.formats[r.nextInt(formats.length)]);
        layout_images.setAttribute("horizontal_offset", String.valueOf(r.nextInt(20)));
        layout_images.setAttribute("vertical_offset", String.valueOf(r.nextInt(20)));
        if (isGenerateElement()) layout_images.setAttribute("description", "description_" + r.nextInt(40));
        if (isGenerateElement()) layout_images.setAttribute("copyright", "copyright_" + r.nextInt(40));
        if (isGenerateElement()) layout_images.setAttribute("notes", "notes_" + r.nextInt(40));
        return layout_images;
    }

    private Element generate_random_custom_key_signature(Document doc, NodeList events){
        String[] accidental = {"none", "double_flat", "flat_and_a_half", "flat", "demiflat", "natural", "demisharp", "sharp", "sharp_and_a_half", "double_sharp"};
        Element custom_key_signature = doc.createElement("custom_key_signature");
        ArrayList<String> idEvents = getIds(events);
        
        custom_key_signature.setAttribute("event_ref", idEvents.get(new Random().nextInt(idEvents.size())));

        int number_key_accidental = r.nextInt(10);
        String[] steps = {"A", "B", "C", "D", "E", "F", "G"};
        for(int i = 0; i < number_key_accidental; i++){
            Element key_accidental = doc.createElement("key_accidental");
            key_accidental.setAttribute("step", steps[r.nextInt(steps.length)]);
            key_accidental.setAttribute("accidental", accidental[r.nextInt(accidental.length)]);
            custom_key_signature.appendChild(key_accidental);
        }
        return custom_key_signature;
    }

    private Element generate_random_key_signature(Document doc, int id, NodeList events){
        Element key_signature = doc.createElement("key_signature");
        ArrayList<String> idEvents = getIds(events);
        
        key_signature.setAttribute("event_ref", idEvents.get(new Random().nextInt(idEvents.size())));
        Element num;
        if(isGenerateElement()) num = doc.createElement("sharp_num");
        else num = doc.createElement("flat_num");
        String[] number = {"0", "1", "2", "3", "4", "5", "6", "7"};
        num.setAttribute("number", number[r.nextInt(number.length)]);
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
        
        ArrayList<String> idEvents = getIds(events);
        if(isGenerateElement()) hairpin.setAttribute("id", String.valueOf(id));
        hairpin.setAttribute("start_event_ref", idEvents.get(r.nextInt(idEvents.size())));
        hairpin.setAttribute("end_event_ref", idEvents.get(r.nextInt(idEvents.size())));
        ArrayList<String> idStaffs = getIds(staffs);
        if(isGenerateElement()) hairpin.setAttribute("staff_ref", idStaffs.get(r.nextInt(idStaffs.size())));
        String[] type = {"crescendo", "diminuendo"};
        hairpin.setAttribute("type", type[r.nextInt(type.length)]);
        return hairpin;
    }


    private Element generate_random_gregorian_symbol(Document doc, int id, NodeList events){
        Element gregorian_symbol = doc.createElement("gregorian_symbol");
        
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
        gregorian_symbol.setAttribute("event_ref", idEvents.get(r.nextInt(idEvents.size())));
        gregorian_symbol.setAttribute("neume", neume[r.nextInt(neume.length)]);
        gregorian_symbol.setAttribute("subtripunctis", subtripunctis[r.nextInt(subtripunctis.length)]);
        gregorian_symbol.setAttribute("interpretative_mark", interpretative_mark[r.nextInt(interpretative_mark.length)]);
        gregorian_symbol.setAttribute("mora", mora[r.nextInt(mora.length)]);
        gregorian_symbol.setAttribute("inflextion", inflextion[r.nextInt(inflextion.length)]);
        return gregorian_symbol;
    }

    private Element generate_random_graphic_instance_group(Document doc){
        Element graphic_instance_group = doc.createElement("graphic_instance_group");
        graphic_instance_group.setAttribute("description", "description_" + new Random().nextInt(30));
        return  graphic_instance_group;
    }

    private Element generate_random_graphic_instance(Document doc, NodeList events){
        
        Element graphic_instance = doc.createElement("graphic_instance");
        String[] measurement_unit = {"centimeters", "millimeters", "inches", "decimal_inches", "points", "picas", "pixels", "twips"};
        if(isGenerateElement()) graphic_instance.setAttribute("description", "description" + r.nextInt(20));
        graphic_instance.setAttribute("position_in_group", "position_" + r.nextInt(50));
        graphic_instance.setAttribute("file_name", "file_" + r.nextInt(50));
        graphic_instance.setAttribute("position_in_group", "position_" + r.nextInt(50));

        graphic_instance.setAttribute("file_format", formats[r.nextInt(formats.length)]);
        graphic_instance.setAttribute("encoding_format", formats[r.nextInt(formats.length)]);
        graphic_instance.setAttribute("measurement_unit", measurement_unit[r.nextInt(measurement_unit.length)]);
        return graphic_instance;
    }


    private Element generate_random_graphic_event(Document doc, NodeList events){
        Element graphic_event = doc.createElement("graphic_event");
        
        ArrayList<String> idEvents = getIds(events);
        graphic_event.setAttribute("event_ref", idEvents.get(r.nextInt(idEvents.size())));
        graphic_event.setAttribute("upper_left_x", String.valueOf(r.nextInt(20)));
        graphic_event.setAttribute("upper_left_y", String.valueOf(r.nextInt(20)));
        graphic_event.setAttribute("lower_right_x", String.valueOf(r.nextInt(20)));
        graphic_event.setAttribute("lower_right_x", String.valueOf(r.nextInt(20)));
        if(isGenerateElement())  graphic_event.setAttribute("highlight_color", "color_" + r.nextInt(255));
        if(isGenerateElement()) graphic_event.setAttribute("description", "description" + r.nextInt(20));
        return graphic_event;
    }

    private Element generate_random_glissando(Document doc, int id, NodeList events){
        Element glissando = doc.createElement("glissando");
        
        ArrayList<String> idEvents = getIds(events);
        if(isGenerateElement()) glissando.setAttribute("id", String.valueOf(id));
        glissando.setAttribute("start_event_ref", idEvents.get(r.nextInt(idEvents.size())));
        if(isGenerateElement()) glissando.setAttribute("end_event_ref", idEvents.get(r.nextInt(idEvents.size())));
        return glissando;
    }

    private Element generate_random_genres(Document doc){
        
        Element genres = doc.createElement("genres");
        int number_genre = r.nextInt(10);
        for(int i = 0; i < number_genre; i++) {
            Element genre = doc.createElement("genre");
            genre.setAttribute("name", "name_" + r.nextInt(20));
            if (isGenerateElement()) genre.setAttribute("description", "description_" + r.nextInt(20));
            if (isGenerateElement()) genre.setAttribute("weight", "weight_" + r.nextInt(20));
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
        
        if(isGenerateElement()) fermata.setAttribute("id", String.valueOf(id));

        ArrayList<String> idEvents = getIds(events);
        fermata.setAttribute("event_ref", idEvents.get(r.nextInt(idEvents.size())));

        return fermata;
    }


    private Element generate_feature_object(Document doc, int id){
        
        Element feature_object = doc.createElement("feature_object");
        if(isGenerateElement()) feature_object.setAttribute("id", String.valueOf(id));
        feature_object.setAttribute("name", "name_" + r.nextInt(50));
        return feature_object;
    }

    private Element generate_random_event(Document doc, int id){
        
        Element event = doc.createElement("event");
        event.setAttribute("id", "event" + String.valueOf(id));
        event.setAttribute("timing", String.valueOf(r.nextInt(256)));
        event.setAttribute("hpos", String.valueOf(r.nextInt(256)));
        return event;
}

    private Element generate_random_end(Document doc, int id, NodeList events){
        Element end = doc.createElement("ref");
        
        ArrayList<String> idEvents = getIds(events);
        if(isGenerateElement()) end.setAttribute("id", String.valueOf(id));
        end.setAttribute("event_ref", idEvents.get(r.nextInt(idEvents.size())));
        return end;
    }

    private Element generate_random_dynamic(Document doc, int id, NodeList events, NodeList staffs){
        
        String[] extension_line_shape = {"normal", "dotted", "dashed"};
        Element dynamic = doc.createElement("dynamic");
        ArrayList<String> idEvents = getIds(events);
        ArrayList<String> idStaffs = getIds(staffs);
        dynamic.setAttribute("id", String.valueOf(id));
        if(isGenerateElement()) dynamic.setAttribute("extension_line_to", idEvents.get(r.nextInt(idEvents.size())));
        if(isGenerateElement()) dynamic.setAttribute("staff_ref", idStaffs.get(r.nextInt(idStaffs.size())));
        dynamic.setAttribute("event_ref", idEvents.get(r.nextInt(idEvents.size())));
        if(isGenerateElement()) dynamic.setAttribute("extension_line_shape", extension_line_shape[r.nextInt(extension_line_shape.length)]);
        return dynamic;
    }

    private Element generate_random_duration(Document doc){
        
        Element duration = doc.createElement("duration");
        duration.setAttribute("num", String.valueOf(r.nextInt(5)));
        duration.setAttribute("den", String.valueOf(r.nextInt(5)));
        if(isGenerateElement()){
            Element tuple_ratio = doc.createElement("tuple_ratio");
            tuple_ratio.setAttribute("enter_num", String.valueOf(r.nextInt(5)));
            tuple_ratio.setAttribute("enter_den", String.valueOf(r.nextInt(8)));
            tuple_ratio.setAttribute("in_num", String.valueOf(r.nextInt(5)));
            tuple_ratio.setAttribute("in_den", String.valueOf(r.nextInt(5)));
            if(isGenerateElement()) tuple_ratio.setAttribute("in_dots", String.valueOf(r.nextInt(5)));
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
        
        ArrayList<String> idEvents = getIds(events);
        if(isGenerateElement()) custom_hsymbol.setAttribute("id", String.valueOf(id));
        custom_hsymbol.setAttribute("start_event_ref", idEvents.get(r.nextInt(idEvents.size())));
        custom_hsymbol.setAttribute("end_event_ref", idEvents.get(r.nextInt(idEvents.size())));
        return custom_hsymbol;
    }

    private Element generate_random_csound_spine_ref(Document doc, NodeList events){
        
        Element csound_spine_ref = doc.createElement("csound_spine_ref");

        ArrayList<String> idEvents = getIds(events);
        csound_spine_ref.setAttribute("start_event_ref", idEvents.get(r.nextInt(idEvents.size())));
        csound_spine_ref.setAttribute("end_event_ref", idEvents.get(r.nextInt(idEvents.size())));
        return  csound_spine_ref;
    }

    private Element generate_random_csound_spine_event(Document doc, NodeList events){
        
        Element csound_spine_event = doc.createElement("csound_spine_event");

        ArrayList<String> idEvents = getIds(events);
        csound_spine_event.setAttribute("start_event_ref", idEvents.get(r.nextInt(idEvents.size())));
        csound_spine_event.setAttribute("end_event_ref", idEvents.get(r.nextInt(idEvents.size())));
        csound_spine_event.setAttribute("line", String.valueOf(r.nextInt(40)));
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
        
        Element csound_instrument_mapping = doc.createElement("csound_instrument_mapping");
        csound_instrument_mapping.setAttribute("instrument_number", String.valueOf(r.nextInt(20)));
        if(isGenerateElement()) csound_instrument_mapping.setAttribute("start_line", String.valueOf(r.nextInt(50)));
        if(isGenerateElement()) csound_instrument_mapping.setAttribute("end_line", String.valueOf(r.nextInt(50)));
        if(isGenerateElement()) csound_instrument_mapping.setAttribute("pnml_line", "file_" + String.valueOf(r.nextInt(50)));
        return csound_instrument_mapping;

    }
    private Element generate_random_csound_instance(Document doc){
        return doc.createElement("csound_instance");
    }

    private Element generate_random_fine(Document doc, int id, NodeList events){
        Element fine = doc.createElement("fine");
        
        ArrayList<String> idEvents = getIds(events);
        fine.setAttribute("start_event_ref", idEvents.get(r.nextInt(idEvents.size())));
        fine.setAttribute("end_event_ref", idEvents.get(r.nextInt(idEvents.size())));
        if(isGenerateElement()) fine.setAttribute("id", String.valueOf(id));
        return fine;
    }

    private Element generate_random_segno(Document doc, int id, NodeList events){
        Element segno = doc.createElement("segno");
        
        ArrayList<String> idEvents = getIds(events);
        segno.setAttribute("start_event_ref", idEvents.get(r.nextInt(idEvents.size())));
        segno.setAttribute("end_event_ref", idEvents.get(r.nextInt(idEvents.size())));
        if(isGenerateElement()) segno.setAttribute("id", String.valueOf(id));
        return segno;
    }

    private Element generate_random_coda(Document doc, int id, NodeList events){
        Element coda = doc.createElement("coda");
        
        ArrayList<String> idEvents = getIds(events);
        coda.setAttribute("start_event_ref", idEvents.get(r.nextInt(idEvents.size())));
        coda.setAttribute("end_event_ref", idEvents.get(r.nextInt(idEvents.size())));
        if(isGenerateElement()) coda.setAttribute("id", String.valueOf(id));
        return coda;
    }

    private Element generate_random_clef(Document doc, NodeList events){
        Element clef = doc.createElement("clef");
        
        ArrayList<String> idEvents = getIds(events);
        clef.setAttribute("start_event_ref", idEvents.get(r.nextInt(idEvents.size())));
        clef.setAttribute("end_event_ref", idEvents.get(r.nextInt(idEvents.size())));
        clef.setAttribute("staff_step", String.valueOf(r.nextInt(20)));
        String[] shape = {"G", "F", "C", "gregorian_F", "gregorian_C", "percussion", "doubleG", "tabguitar"};
        String[] octave_num = {"0", "8", "-8", "15"};
        clef.setAttribute("shape", shape[r.nextInt(shape.length)]);
        clef.setAttribute("octave_num", octave_num[r.nextInt(octave_num.length)]);
        return clef;
    }

    private Element generate_random_chord(Document doc, int id, NodeList events){
        String[] stem_direction = {"up", "down", "none"};
        String[] beam_and_cue = {"yes", "no"};
        String[] tremolo_lines = {"no", "1", "2", "3", "4", "5", "6"};
        ArrayList<String> idEvents = getIds(events);
        
        Element chord = doc.createElement("chord");
        chord.setAttribute("id", String.valueOf(id));
        chord.setAttribute("event_ref", idEvents.get(r.nextInt(idEvents.size())));
        if(isGenerateElement())  chord.setAttribute("stem_direction", stem_direction[r.nextInt(stem_direction.length)]);
        if(isGenerateElement()) chord.setAttribute("beam_before", beam_and_cue[r.nextInt(beam_and_cue.length)]);
        if(isGenerateElement()) chord.setAttribute("beam_before", beam_and_cue[r.nextInt(beam_and_cue.length)]);
        chord.setAttribute("tremolo_lines", tremolo_lines[r.nextInt(tremolo_lines.length)]);
        return chord;
    }

    private Element generate_random_chord_symbol(Document doc, int id, NodeList events){
        
        Element chord_symbol = doc.createElement("chord_symbol");
        if(isGenerateElement()) chord_symbol.setAttribute("id", String.valueOf(id));
        ArrayList<String> idEvents = getIds(events);
        chord_symbol.setAttribute("start_event_ref", idEvents.get(r.nextInt(idEvents.size())));
        chord_symbol.setAttribute("end_event_ref", idEvents.get(r.nextInt(idEvents.size())));
        return chord_symbol;
    }

    private Element generate_random_chord_grid(Document doc, int id){
        
        Element chord_grid = doc.createElement("chord_grid");
        if(isGenerateElement()) chord_grid.setAttribute("id", String.valueOf(id));
        if(isGenerateElement()) chord_grid.setAttribute("author", "author_" + r.nextInt(20));
        if(isGenerateElement()) chord_grid.setAttribute("description", "description_" + r.nextInt(20));
        int number_chord_name = r.nextInt(10);
        for(int i = 0; i < number_chord_name; i++){
            Element chord_name = doc.createElement("chord_name");
            chord_name.setAttribute("root_id", String.valueOf(i));
            chord_grid.appendChild(chord_name);
        }
        return chord_grid;
    }

    private Element generate_random_breath_mark(Document doc, int id, NodeList events, NodeList staffs){
        
        ArrayList<String> idEvents = getIds(events);
        ArrayList<String> idStaffs = getIds(staffs);
        Element breath_mark = doc.createElement("breath_mark");
        breath_mark.setAttribute("id", String.valueOf(id));

        if(isGenerateElement()) breath_mark.setAttribute("staff_ref", idStaffs.get(r.nextInt(idStaffs.size())));
        breath_mark.setAttribute("start_event_ref", idEvents.get(r.nextInt(idEvents.size())));
        breath_mark.setAttribute("end_event_ref", idEvents.get(r.nextInt(idEvents.size())));
        String[] type = {"comma", "caesura"};
        breath_mark.setAttribute("type", type[r.nextInt(type.length)]);
        return breath_mark;
    }

    private Element generate_random_brackets(Document doc){
        
        Element brackets = doc.createElement("brackets");
        String[] marker = {"start_of_staff_group", "end_of_staff_group"};
        String[] shape = {"square", "rounded_square", "brace", "vertical_bar", "none"};
        brackets.setAttribute("marker", marker[r.nextInt(marker.length)]);
        brackets.setAttribute("group_number", "group_" + r.nextInt(20));
        brackets.setAttribute("shape", shape[r.nextInt(shape.length)]);
        return brackets;
    }

    private Element generate_random_bend(Document doc, int id, NodeList events){
        Element bend = doc.createElement("bend");
        String[] type = {"single", "double"};
        String[] pitch = {"A", "B", "C", "D", "E", "F", "up", "down"};
        String[] accidental = {"none", "double_flat", "flat_and_a_half", "flat", "demiflat", "natural", "demisharp", "sharp", "sharp_and_a_half", "double_sharp"};
        
        if(isGenerateElement()) bend.setAttribute("id", String.valueOf(id));
        ArrayList<String> idEvents = getIds(events);
        bend.setAttribute("event_ref", idEvents.get(r.nextInt(idEvents.size())));
        bend.setAttribute("to_pitch", pitch[r.nextInt(pitch.length)]);
        bend.setAttribute("type", type[r.nextInt(type.length)]);
        if(isGenerateElement()) bend.setAttribute("to_accindental", accidental[r.nextInt(accidental.length)]);
        if(isGenerateElement()) bend.setAttribute("to_octave", String.valueOf(r.nextInt(12)));
        return bend;
    }

    private Element generate_random_barre(Document doc){
        
        Element barre = doc.createElement("barre");
        barre.setAttribute("start_string_position", "start_string_" + r.nextInt(20));
        barre.setAttribute("end_string_position", "end_string_" + r.nextInt(20));
        barre.setAttribute("freat_position", "freat_string_" + r.nextInt(20));
        return barre;
    }

    private Element generate_random_baroque_appoggiatura(Document doc, int id, NodeList events){
        Element baroque_appoggiatura = doc.createElement("baroque_appoggiatura");
        
        String[] style = {"hairpin", "plus", "pipe", "double_slur", "slash", "backslash", "up_hook", "down_hook"};
        ArrayList<String> idEvents = getIds(events);
        baroque_appoggiatura.setAttribute("event_ref", idEvents.get(r.nextInt(idEvents.size())));
        if(isGenerateElement()) baroque_appoggiatura.setAttribute("id", String.valueOf(id));
        baroque_appoggiatura.setAttribute("style", style[r.nextInt(style.length)]);
        return baroque_appoggiatura;
    }

    private Element generate_random_baroque_acciaccatura(Document doc, int id, NodeList events){
        Element baroque_acconciatura = doc.createElement("baroque_acconciatura");
        
        String[] style = {"vertical_turn", "mordent", "flatte", "tierce_coulee", "slash", "backslash"};
        ArrayList<String> idEvents = getIds(events);
        baroque_acconciatura.setAttribute("event_ref", idEvents.get(r.nextInt(idEvents.size())));
        if(isGenerateElement()) baroque_acconciatura.setAttribute("id", String.valueOf(id));
        baroque_acconciatura.setAttribute("style", style[r.nextInt(style.length)]);
        return baroque_acconciatura;

    }
    private Element generate_random_barline(Document doc, NodeList events){
        Element barline = doc.createElement("barline");
        
        String[] style = {"dashed", "double", "final", "invisible", "standard", "smaller", "minimum"};
        String[] extension = {"single_staff", "staff_group", "all_staves", "mensurstrich"};
        ArrayList<String> idEvents = getIds(events);
        barline.setAttribute("event_ref", idEvents.get(r.nextInt(idEvents.size())));
        barline.setAttribute("style", style[r.nextInt(style.length)]);
        barline.setAttribute("extension", style[r.nextInt(extension.length)]);
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

    private Element generate_random_arpeggio(Document doc){
        Element arpeggio = doc.createElement("arpeggio");
        
        String[] shape = {"wavy", "line", "no_arpeggio"};
        String[] direction = {"up", "down"};
        arpeggio.setAttribute("shape", shape[r.nextInt(shape.length)]);
        arpeggio.setAttribute("direction", direction[r.nextInt(direction.length)]);
        return arpeggio;
    }

    private Element generate_random_notehead_ref(Document doc, NodeList events){
        
        Element notehead_ref = doc.createElement("notehead_ref");
        ArrayList<String> idEvents = getIds(events);
        notehead_ref.setAttribute("event_ref", idEvents.get(r.nextInt(idEvents.size())));
        return notehead_ref;
    }

    private Element generate_random_appoggiatura(Document doc, int id, NodeList events){
        Element appoggiatura = doc.createElement("appoggiatura");
        String[] slur = {"yes", "no"};
        
        Element analysis = doc.createElement("appoggiatura");
        if(isGenerateElement()) analysis.setAttribute("id", String.valueOf(id));
        appoggiatura.setAttribute("slur", slur[r.nextInt(slur.length)]);
        ArrayList<String> idEvents = getIds(events);
        appoggiatura.setAttribute("event_ref", idEvents.get(r.nextInt(idEvents.size())));
        return appoggiatura;
    }

    private Element generate_random_analysis(Document doc, int id){
        
        Element analysis = doc.createElement("doc");
        if(isGenerateElement()) analysis.setAttribute("id", String.valueOf(id));
        if(isGenerateElement()) analysis.setAttribute("author", "author_" + r.nextInt(100));
        if(isGenerateElement()) analysis.setAttribute("description", "description_" + r.nextInt(100));
        return analysis;
    }

    private Element generate_random_analog_media(Document doc){
        Element analog_media = doc.createElement("analog_media");
        return analog_media;
    }


    private Element generate_random_analog_medium(Document doc){
        
        Element analog_medium = doc.createElement("analog_medium");
        analog_medium.setAttribute("description", "description_" + r.nextInt(200));
        if(isGenerateElement()) analog_medium.setAttribute("copyright", "copyright_" + r.nextInt(200));
        if(isGenerateElement()) analog_medium.setAttribute("notes", "notes_" + r.nextInt(200));
        return analog_medium;
    }


    private Element generate_random_album(Document doc){
        
        Element album = doc.createElement("album");
        album.setAttribute("title", "album_" + r.nextInt(100));
        if(isGenerateElement()) album.setAttribute("carrier_type", "carrier_" + r.nextInt(200));
        if(isGenerateElement()) album.setAttribute("catalogue_number", String.valueOf(r.nextInt(200)));
        if(isGenerateElement()) album.setAttribute("number_of_tracks", String.valueOf(r.nextInt(200)));
        if(isGenerateElement()) album.setAttribute("pubblication_date", generate_date());
        if(isGenerateElement()) album.setAttribute("label", "label_" + r.nextInt(200));
        return album;
    }

    private Element generate_random_albums(Document doc){
        Element albums = doc.createElement("albums");
        return albums;
    }

    private Element generate_random_agogics(Document doc, int id, NodeList events){
        
        Element agogics = doc.createElement("agogics");
        String[] bracketed = {"yes", "no"};
        if(isGenerateElement()) agogics.setAttribute("bracketed", bracketed[r.nextInt(bracketed.length)]);
        ArrayList<String> idEvents = getIds(events);
        agogics.setAttribute("event_ref", idEvents.get(r.nextInt(idEvents.size())));
        return agogics;
    }

    private Element generate_random_acciaccatura(Document doc, int id, NodeList events){
        
        String[] slur = {"yes", "no"};
        Element acciaccatura = doc.createElement("acciaccatura");

        if(isGenerateElement()) acciaccatura.setAttribute("id", String.valueOf(id));

        ArrayList<String> idEvents = getIds(events);

        acciaccatura.setAttribute("event_ref", idEvents.get(r.nextInt(idEvents.size())));
        acciaccatura.setAttribute("slur", slur[r.nextInt(slur.length)]);

        return acciaccatura;
    }


    // vedere SaveMelody.java nel file xml.pdf
    // <pitch octave="6" step="D" actual_accidental="natural" />
    private Element generate_random_pitch(Document doc){
        
        ArrayList<String> notes = configuration.getNotes();
        int[] max_min_height = configuration.getMin_max_height();
        ArrayList<String> accidentals = configuration.getAccidentals();
        int[] max_min_numerator = configuration.getMin_max_numerator();
        int[] max_min_denominator = configuration.getMin_max_denominator();
        Note n = new Note();
        n.accidental = AccidentalEnum.valueOf(accidentals.get(r.nextInt(accidentals.size())));
        n.octave = r.nextInt(max_min_height[1] - max_min_height[0]) + max_min_height[0];
        n.pitch = PitchEnum.valueOf(notes.get(r.nextInt(notes.size())));
        n.numerator = r.nextInt(max_min_numerator[1] - max_min_numerator[0]) + max_min_numerator[0];
        n.denominator = r.nextInt(max_min_denominator[1] - max_min_denominator[0]) + max_min_denominator[0];
        Element pitch = doc.createElement("pitch");
        pitch.setAttribute("actual_accidental", ((AccidentalEnum)(n.accidental)).name().toLowerCase());
        pitch.setAttribute("step", ((PitchEnum)(n.pitch)).name());
        pitch.setAttribute("octave", String.valueOf(n.octave));
        return pitch;
    }



    private ArrayList<String> getIds(NodeList elements){
        
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
