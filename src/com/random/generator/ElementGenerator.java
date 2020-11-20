package com.random.generator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public class ElementGenerator {


    private String[] formats = {"application_excel", "application_mac-binhex40", "application_msword", "application_octet-stream", "application_pdf", "application_x-director", "application_x-gzip", "application_x-javascript", "application_x-macbinary", "application_x-pn-realaudio", "application_x-shockwave_flash", "application_x-tar", "application_zip", "audio_aiff", "audio_avi", "audio_mp3", "audio_mpeg", "audio_mpeg3", "audio_mpg", "audio_wav", "audio_x_aiff", "audio_x_midi", "audio_x_wav", "audio_x-mp3", "audio_x-mpeg", "audio_x-mpeg3", "audio_x-mpegaudio", "audio_x-mpg", "audio_x-ms-wma", "image_avi", "image_bmp", "image_x-bmp", "image_x-bitmap", "image_x-xbitmap", "image_x-win-bitmap", "image_x-windows-bmp", "image_ms-bmp", "image_x-ms-bmp", "application_bmp", "application_x-bmp", "application_x-win-bitmap", "application_preview", "image_gif", "image_jpeg", "image_pict", "image_png", "application_png", "application_x-png", "image_tiff", "text_html", "text_plain_application_postscript", "video_avi", "video_mpeg", "video_msvideo", "video_quicktime", "video_x-msvideo", "video_x-ms-wmv", "video_x-qtc", "video_xmpg2"};
    private Random r;
    private Parameter configuration;
    private int number_elements;
    private int id_gen;

    ArrayList<String> events = new ArrayList<>();
    ArrayList<String> parts = new ArrayList<>();
    ArrayList<String> staffs = new ArrayList<>();
    ArrayList<String> feature_objects = new ArrayList<>();
    ArrayList<String> voices = new ArrayList<>();
    ArrayList<String> segments = new ArrayList<>();
    ArrayList<String> domains = new ArrayList<>();
    ArrayList<String> codomains = new ArrayList<>();
    ArrayList<String> feature_object_relationship = new ArrayList<>();
    ArrayList<String> voice_items = new ArrayList<>();


    public ElementGenerator(Parameter configuration, int number_elements) {
        this.configuration = configuration;
        this.number_elements = number_elements;
        this.id_gen = 0;
        this.r = new Random();
    }

    private boolean isGenerateElement() {
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

    private String generate_time(){
        final Random random = new Random();
        final int millisInDay = 24*60*60*1000;
        Time time = new Time(random.nextInt(millisInDay));
        return time.toString();
    }

    public String id_generator(){
        int temp = this.id_gen;
        id_gen += 1;
        return String.valueOf(temp);
    }


    public Element generate_random_author(Document doc){
        Element author = doc.createElement("author");
        author.setTextContent("author_" + r.nextInt(100));
        return author;
    }

    public Element generate_random_work_title(Document doc){
        return doc.createElement("work_title");
    }

    public Element generate_random_work_number(Document doc){
        return doc.createElement("work_number");
    }

    public Element generate_random_voice_list(Document doc){
        return doc.createElement("voice_list");
    }

    public Element generate_random_voice_item(Document doc){
        Element voice_item = doc.createElement("voice_item");
        voice_item.setAttribute("id", "id_" + id_generator());
        if(staffs.size() > 0) {
            voice_item.setAttribute("staff_ref", staffs.get(r.nextInt(staffs.size())));
        }
        String[] style = {"normal", "rhythmic", "slash", "blank"};
        if(isGenerateElement()) voice_item.setAttribute("notation_style", style[r.nextInt(style.length)]);
        return voice_item;
    }


    public Element generate_random_voice(Document doc){
        String[] ossia = {"yes", "no"};
        Element voice = doc.createElement("voice");
        voice.setAttribute("ossia", ossia[r.nextInt(ossia.length)]);
        if(voice_items.size() > 0)
            voice.setAttribute("voice_item_ref", voice_items.get(r.nextInt(voice_items.size())));
        return voice;
    }


    public Element generate_random_turn(Document doc){
        String[] accidental = {"none", "double_flat", "flat_and_a_half", "flat", "demiflat", "natural", "demisharp", "sharp", "sharp_and_a_half", "double_sharp"};
        String[] style = {"normal","inverted","cut","vertical"};
        String[] type = {"over", "after"};
        Element turn = doc.createElement("turn");
        turn.setAttribute("id", id_generator());
        if(events.size() > 0)
            turn.setAttribute("event_ref", events.get(r.nextInt(events.size())));
        turn.setAttribute("type", type[r.nextInt(type.length)]);
        turn.setAttribute("style", style[r.nextInt(style.length)]);
        turn.setAttribute("upper_accidental", accidental[r.nextInt(accidental.length)]);
        turn.setAttribute("lower_accidental", accidental[r.nextInt(accidental.length)]);
        return turn;
    }

    /*public Element generate_random_tuplet_ratio(Document doc){
        Element tuplet_ratio = doc.createElement("tuplet_ratio");
        tuplet_ratio.setAttribute("enter_num", String.valueOf(r.nextInt(8)));
        tuplet_ratio.setAttribute("enter_den", String.valueOf(r.nextInt(8)));
        tuplet_ratio.setAttribute("enter_dots", String.valueOf(r.nextInt(8)));
        tuplet_ratio.setAttribute("in_num", String.valueOf(r.nextInt(8)));
        tuplet_ratio.setAttribute("in_den", String.valueOf(r.nextInt(8)));
        if(isGenerateElement()) tuplet_ratio.setAttribute("in_dots", String.valueOf(r.nextInt(8)));
        return tuplet_ratio;
    }*/


    public Element generate_random_trill(Document doc){
        Element trill = doc.createElement("trill");
        if(isGenerateElement()) trill.setAttribute("id", id_generator());
        if(events.size() > 0) trill.setAttribute("event_ref", events.get(r.nextInt(events.size())));
        String[] hooks = {"up", "none", "down"};
        String[] accidental = {"none", "double_flat", "flat_and_a_half", "flat", "demiflat", "natural", "demisharp", "sharp", "sharp_and_a_half", "double_sharp"};
        trill.setAttribute("accidental", accidental[r.nextInt(accidental.length)]);

        String[] style = ("t,tr,tr-,plus,double_slash,caesura_double_slash,slur_double_slash,mordent,double_mordent").split(",");
        if(isGenerateElement()) trill.setAttribute("style", style[r.nextInt(style.length)]);
        trill.setAttribute("start_hook", hooks[r.nextInt(hooks.length)]);
        trill.setAttribute("end_hook", hooks[r.nextInt(hooks.length)]);
        return  trill;
    }

    public Element generate_random_tremolo(Document doc){
        Element tremolo = doc.createElement("tremolo");
        if(isGenerateElement()) tremolo.setAttribute("id", id_generator());
        if(events.size() > 0) {
            tremolo.setAttribute("start_event_ref", events.get(r.nextInt(events.size())));
            tremolo.setAttribute("end_event_ref", events.get(r.nextInt(events.size())));
        }
        tremolo.setAttribute("tremolo_lines", String.valueOf(r.nextInt(6) + 1));
        return tremolo;
    }

    public Element generate_random_transition(Document doc){
        Element transition = doc.createElement("transition");
        transition.setAttribute("transition_ref", "transition_" + r.nextInt(30));
        if(feature_object_relationship.size() > 0)
            transition.setAttribute("feature_object_relationship_ref", feature_object_relationship.get(r.nextInt(feature_object_relationship.size())));
        return transition;
    }

    public Element generate_random_track_region(Document doc){
        Element track_region = doc.createElement("track_region");
        track_region.setAttribute("name", "name_" + r.nextInt(20));
        if(isGenerateElement()) track_region.setAttribute("description", "description_" + r.nextInt(20));
        if(events.size() > 0) {
            track_region.setAttribute("start_event_ref", events.get(r.nextInt(events.size())));
            track_region.setAttribute("end_event_ref", events.get(r.nextInt(events.size())));
        }
        return track_region;
    }

    public Element generate_random_track_indexing(Document doc){
        Element track_indexing = doc.createElement("track_indexing");
        String[] timing_type = {"samples", "time", "seconds", "time_frames", "frames", "measures", "smpte_24", "smpte_25", "smpte_2997", "smpte_30"};
        track_indexing.setAttribute("timing_type", timing_type[r.nextInt(timing_type.length)]);
        return track_indexing;
    }

    public Element generate_random_track_general(Document doc){
        Element track_general = doc.createElement("track_general");
        if(isGenerateElement()) track_general.setAttribute("geographical_region", "region_" + r.nextInt());
        if(isGenerateElement()) track_general.setAttribute("lyrics_language", "language_" + r.nextInt(number_elements));
        return track_general;
    }

    public Element generate_random_track_event(Document doc){
        Element track_event = doc.createElement("track_event");
        track_event.setAttribute("start_time", generate_time());
        if(isGenerateElement()) track_event.setAttribute("end_time", generate_time());
        if(events.size() > 0) track_event.setAttribute("event_ref", events.get(r.nextInt(events.size())));
        if(isGenerateElement()) track_event.setAttribute("description", "description_" + r.nextInt(100));
        return track_event;
    }

    public Element generate_random_time_signature(Document doc){
        Element time_signature = doc.createElement("time_signature");
        String[] visible = {"yes", "no"};
        if(events.size() > 0) time_signature.setAttribute("event_ref", events.get(r.nextInt(events.size())));
        time_signature.setAttribute("visible", visible[r.nextInt(visible.length)]);
        return time_signature;
    }

    public Element generate_random_time_indication(Document doc){
        Element time_indication = doc.createElement("time_indication");
        String[] dur = configuration.generate_duration();
        if(isGenerateElement()) time_indication.setAttribute("den", dur[1]);
        time_indication.setAttribute("num", dur[0]);
        String[] abbrevation = {"yes", "no"};
        if(isGenerateElement()) time_indication.setAttribute("vtu_amount", String.valueOf(r.nextInt(40)));
        time_indication.setAttribute("abbreviation", abbrevation[r.nextInt(abbrevation.length)]);
        return time_indication;
    }

    public Element generate_random_tie(Document doc){
        return doc.createElement("tie");
    }


    public Element generate_random_text_font(Document doc){
        return doc.createElement("text_font");
    }

    public Element generate_random_text_field(Document doc){
        Element text_field = doc.createElement("text_field");
        String[] shape = {"normal", "dotted", "dashed"};
        if(events.size() > 0)
            text_field.setAttribute("event_ref", events.get(r.nextInt(events.size())));
        if(events.size()>0)
            if(isGenerateElement()) text_field.setAttribute("extension_line_to", events.get(r.nextInt(events.size())));
        if(isGenerateElement()) text_field.setAttribute("extension_line_shape", shape[r.nextInt(shape.length)]);
        return text_field;
    }

    public Element generate_random_tablature_tuning(Document doc){
        Element tablature_tuning = doc.createElement("tablature_tuning");
        String[] type = {"D", "E", "G", "A", "baroque", "flat_french", "other"};
        if(isGenerateElement()) tablature_tuning.setAttribute("type", type[r.nextInt(type.length)]);
        return tablature_tuning;
    }

    public Element generate_random_tablature_symbol(Document doc){
        Element tablature_symbol = doc.createElement("tablature_symbol");
        String[] yn = {"yes", "no"};
        String[] stem_direction = {"up", "down", "none"};
        if(isGenerateElement()) tablature_symbol.setAttribute("id", "tablature_symbol_" + id_generator());
        if(events.size() > 0)
            tablature_symbol.setAttribute("event_ref", events.get(r.nextInt(events.size())));
        if(isGenerateElement()) tablature_symbol.setAttribute("stem_direction", stem_direction[r.nextInt(stem_direction.length)]);
        tablature_symbol.setAttribute("beam_before", yn[r.nextInt(yn.length)]);
        tablature_symbol.setAttribute("beam_after", yn[r.nextInt(yn.length)]);
        return tablature_symbol;
    }

    public Element generate_random_tablature_pitch(Document doc){
        Element tablature_pitch = doc.createElement("tablature_pitch");
        if(isGenerateElement()) tablature_pitch.setAttribute("string_number", String.valueOf(r.nextInt(20)));
        tablature_pitch.setAttribute("key_number", String.valueOf(r.nextInt(15)));
        return tablature_pitch;
    }

    public Element generate_random_tablature_hsymbol(Document doc){
        Element tablature_hsymbol = doc.createElement("tablature_hsymbol");
        if(isGenerateElement()) tablature_hsymbol.setAttribute("id", id_generator());
        if(events.size() > 0)
            tablature_hsymbol.setAttribute("event_ref", events.get(r.nextInt(events.size())));
        tablature_hsymbol.setAttribute("string_number", String.valueOf(r.nextInt(20)));
        tablature_hsymbol.setAttribute("start_fret", String.valueOf(r.nextInt(20)));
        tablature_hsymbol.setAttribute("fret_number", String.valueOf(r.nextInt(20)));
        return tablature_hsymbol;
    }

    public Element generate_random_tablature_fingering(Document doc){
        Element tablature_fingering = doc.createElement("tablature_fingering");
        String[] shape = {"number", "dot", "other"};
        tablature_fingering.setAttribute("shape", shape[r.nextInt(shape.length)]);
        return tablature_fingering;
    }

    public Element generate_random_tablature_element(Document doc){
        Element tablature_element = doc.createElement("tablature_element");
        String[] shape = {"empty_circle", "full_circle", "cross", "rhombus", "1", "2", "3", "4", "T"};
        tablature_element.setAttribute("shape", shape[r.nextInt(shape.length)]);
        tablature_element.setAttribute("string_position", String.valueOf(r.nextInt(30)));
        tablature_element.setAttribute("freat_position", String.valueOf(r.nextInt(30)));
        return tablature_element;
    }


    public Element generate_random_tablature_articulation(Document doc){
        Element tablature_articulation = doc.createElement("tablature_articulation");
        String[] shape = {"cross", "tie", "other"};
        tablature_articulation.setAttribute("shape", shape[r.nextInt(shape.length)]);
        return tablature_articulation;
    }

    public Element generate_random_sys_ex(Document doc){
        Element sys_ex = doc.createElement("sys_ex");
        if(events.size() > 0)
            sys_ex.setAttribute("event_ref", events.get(r.nextInt(events.size())));
        return sys_ex;
    }

    public Element generate_random_syllable(Document doc){
        Element syllable = doc.createElement("syllable");
        String[] hyphen = {"yes", "no"};
        if(events.size() > 0) {
            syllable.setAttribute("start_event_ref", events.get(r.nextInt(events.size())));
            syllable.setAttribute("end_event_ref", events.get(r.nextInt(events.size())));
        }
        syllable.setAttribute("hyphen", hyphen[r.nextInt(hyphen.length)]);
        return syllable;
    }

    public Element generate_random_structural(Document doc){
        return doc.createElement("structural");
    }

    public Element generate_random_string(Document doc){
        //String[] pitch = {"A", "B", "C", "D", "E", "F", "G"};
        String[] accidental = {"none", "double_flat", "flat_and_a_half", "flat", "demiflat", "natural", "demisharp", "sharp", "sharp_and_a_half", "double_sharp"};
        Element string = doc.createElement("string");
        string.setAttribute("number", String.valueOf(r.nextInt(200)));
        string.setAttribute("string_pitch", configuration.generate_note_with_probability());
        if(isGenerateElement()) string.setAttribute("string_accidental", accidental[r.nextInt(accidental.length)]);
        string.setAttribute("string_octave", String.valueOf(r.nextInt(number_elements)));
        return string;
    }

    public Element generate_random_custom_page_format(Document doc){
        Element custom_page_format = doc.createElement("custom_page_format");
        custom_page_format.setAttribute("width", String.valueOf(r.nextInt(800)));
        custom_page_format.setAttribute("height", String.valueOf(r.nextInt(800)));
        return custom_page_format;
    }

    public Element generate_random_standard_page_format(Document doc){
        Element standard_page_format = doc.createElement("standard_page_format");
        String[] format = ("a0,a1,a2,a3,a4,a5,a6,a7,a8,b0,b1,b2,b3,b4,b5,b6,b7,b8,c0,c1,c2,c3,c4,c5,c6,c7,c8,ansi_a,ansi_b,ansi_c,ansi_d,ansi_e,arch_a,arch_b,arch_c,arch_e,arch_e1,quarto,foolscap,executive,monarch,government_letter,letter,legal,ledger,tabloid,post,crown,large_post,demy,medium,royal,elephant,double_demy,quad_demy,statement").split(",");
        standard_page_format.setAttribute("format", format[r.nextInt(format.length)]);
        return  standard_page_format;
    }

    public Element generate_random_staff_list(Document doc){
        return doc.createElement("staff_list");
    }

    public Element generate_random_staff(Document doc){
        Element staff = doc.createElement("staff");
        staff.setAttribute("id", "staff_" + id_generator());
        staff.setAttribute("line_number", String.valueOf(r.nextInt(number_elements)));
        String[] ossia = {"yes", "no"};
        staff.setAttribute("ossia", ossia[r.nextInt(ossia.length)]);
        String[] tablature = {"none", "french", "german", "italian"};
        if(isGenerateElement()) staff.setAttribute("tablature", tablature[r.nextInt(tablature.length)]);
        return staff;
    }

    public Element generate_random_spine(Document doc){
        return doc.createElement("spine");
    }

    public Element generate_random_special_beam(Document doc){
        Element special_beam = doc.createElement("special_beam");
        if(isGenerateElement()) special_beam.setAttribute("id", id_generator());
        if(isGenerateElement()) special_beam.setAttribute("fanned_from", String.valueOf(r.nextInt(number_elements)));
        if(isGenerateElement()) special_beam.setAttribute("fanned_to", String.valueOf(r.nextInt(number_elements)));
        return special_beam;
    }

    public Element generate_random_slur(Document doc){
        Element slur = doc.createElement("slur");
        if(isGenerateElement()) slur.setAttribute("id", id_generator());
        String[] shape = {"normal", "dashed", "dotted"};
        String[] bracketed = {"yes", "no"};
        if(events.size() > 0) {
            slur.setAttribute("start_event_ref", events.get(r.nextInt(events.size())));
            slur.setAttribute("end_event_ref", events.get(r.nextInt(events.size())));
        }
        slur.setAttribute("shape", shape[r.nextInt(shape.length)]);
        slur.setAttribute("bracketed", bracketed[r.nextInt(bracketed.length)]);
        return slur;
    }

    public Element generate_random_segment(Document doc){
        Element segment = doc.createElement("segment");
        segment.setAttribute("id", "id_" + id_generator());
        return segment;
    }

    public Element generate_random_segmentation(Document doc){
        Element segmentation = doc.createElement("segmentation");
        if(isGenerateElement()) segmentation.setAttribute("id", "segmentation_" + id_generator());
        if(isGenerateElement()) segmentation.setAttribute("description", "description_" + r.nextInt());
        if(isGenerateElement()) segmentation.setAttribute("method", "method_" + r.nextInt());
        return segmentation;

    }

    public Element generate_random_segment_event(Document doc) {
        Element segment_event = doc.createElement("segment_event");
        if(events.size() > 0)
            segment_event.setAttribute("event_ref", events.get(r.nextInt(events.size())));
        return segment_event;
    }


    public Element generate_random_rights(Document doc){
        Element rights = doc.createElement("rights");
        rights.setAttribute("file_name", "file_" + r.nextInt(100));
        return rights;
    }

    public Element generate_random_rest(Document doc){
        Element rest = doc.createElement("rest");
        String[] hidden = {"yes", "no"};
        if(isGenerateElement()) rest.setAttribute("id", id_generator());
        if(staffs.size() > 0)
            if(isGenerateElement()) rest.setAttribute("staff_ref", staffs.get(r.nextInt(staffs.size())));
        if(events.size() > 0)
            rest.setAttribute("event_ref", events.get(r.nextInt(events.size())));
        if(isGenerateElement()) rest.setAttribute("hidden", hidden[r.nextInt(hidden.length)]);
        return rest;
    }

    public Element generate_random_repetition(Document doc){
        return doc.createElement("repetition");
    }

    public Element generate_random_repeat_text(Document doc){
        return doc.createElement("repeat_text");
    }

    public Element generate_random_repeat(Document doc){
        Element repeat = doc.createElement("repeat");
        if(isGenerateElement()) repeat.setAttribute("id", id_generator());
        if(events.size() > 0)
            repeat.setAttribute("event_ref", events.get(r.nextInt(events.size())));
        return repeat;
    }

    public Element generate_random_relationships(Document doc){
        Element relationships = doc.createElement("relationships");

        int number_relationship = r.nextInt(number_elements)+1;

        for(int i = 0; i < number_relationship; i++) {
            Element relationship = doc.createElement("relationship");
            relationship.setAttribute("id", "relat_" + id_generator());

            if (isGenerateElement()) relationship.setAttribute("description", "description_" + r.nextInt(20));
            if (segments.size() > 0) {
                relationship.setAttribute("segment_a_ref", segments.get(r.nextInt(segments.size())));
                relationship.setAttribute("segment_b_ref", segments.get(r.nextInt(segments.size())));
            }
            if(feature_objects.size() > 0) {
                if (isGenerateElement())
                    relationship.setAttribute("feature_object_a_ref", feature_objects.get(r.nextInt(feature_objects.size())));
                if (isGenerateElement())
                    relationship.setAttribute("feature_object_b_ref", feature_objects.get(r.nextInt(feature_objects.size())));
            }
            if(feature_object_relationship.size() > 0) {
                if (isGenerateElement())
                    relationship.setAttribute("feature_object_relationship_ref", feature_object_relationship.get(r.nextInt(feature_object_relationship.size())));
            }
            relationships.appendChild(relationship);
        }
        return relationships;
    }

    public Element generate_random_feature_object_relationships(Document doc){

        Element feature_object_relationships = doc.createElement("feature_object_relationships");

        int number_relationship = r.nextInt(number_elements)+1;
        for(int i = 0; i < number_relationship; i++){
            Element feature_object_relationship = doc.createElement("feature_object_relationship");
            feature_object_relationship.setAttribute("id", "fo_" + id_generator());
            Element ver_rule = doc.createElement("ver_rule");
            feature_object_relationship.appendChild(ver_rule);
            this.feature_object_relationship.add(feature_object_relationship.getAttribute("id"));
            feature_object_relationships.appendChild(feature_object_relationship);
        }
        return feature_object_relationships;
    }

    public Element generate_random_track(Document doc){
        Element track = doc.createElement("track");
        int number_related_file = r.nextInt(number_elements)+1;
        track.setAttribute("file_name", "file_" + r.nextInt(100));
        track.setAttribute("file_format", formats[r.nextInt(formats.length)]);
        track.setAttribute("encoding_format", formats[r.nextInt(formats.length)]);
        return track;
    }


    public Element generate_random_related_files(Document doc){
        Element related_files = doc.createElement("related_files");
        int number_related_file = r.nextInt(number_elements)+1;
        for(int i = 0; i < number_related_file; i++) {
            Element related_file = doc.createElement("related_file");
            if(events.size() > 0) {
                related_file.setAttribute("start_event_ref", events.get(r.nextInt(events.size())));
                if (isGenerateElement())
                    related_file.setAttribute("end_event_ref", events.get(r.nextInt(events.size())));
            }
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

    public Element generate_random_recordings(Document doc){
        return doc.createElement("recordings");
    }

    public Element generate_random_recording(Document doc){
        Element recording = doc.createElement("recording");
        recording.setAttribute("date", generate_date());
        if(isGenerateElement()) recording.setAttribute("recorded_part", "part_" + r.nextInt(100));
        if(isGenerateElement()) recording.setAttribute("studio_name", "studio_" + r.nextInt(100));
        if(isGenerateElement()) recording.setAttribute("studio_address", "address_" + r.nextInt(100));
        return recording;
    }

    public Element generate_random_printed_accidentals(Document doc){
        Element printed_accidentals = doc.createElement("printed_accidentals");
        String[] accidental = {"double_flat", "flat_and_a_half", "flat", "demiflat", "natural", "demisharp", "sharp", "sharp_and_a_half", "double_sharp"};
        String[] shape = {"normal", "small", "bracketed"};
        for(int i = 0; i < r.nextInt(number_elements)+1; i++){
            printed_accidentals.appendChild(doc.createElement(accidental[r.nextInt(accidental.length)]));
        }
        printed_accidentals.setAttribute("shape", shape[r.nextInt(shape.length)]);
        return printed_accidentals;
    }

    public Element generate_random_place(Document doc){
        Element place = doc.createElement("place");
        if(segments.size() > 0)
            place.setAttribute("segment_ref", segments.get(r.nextInt(segments.size())));
        place.setAttribute("place_ref", "id_" + r.nextInt(100));
        return place;
    }

    public Element generate_random_petri_nets(Document doc){
        return doc.createElement("petri_nets");
    }

    public Element generate_random_petri_net(Document doc){
        Element petri_net = doc.createElement("petri_net");
        petri_net.setAttribute("file_name", "file_" + r.nextInt(100));
        return petri_net;
    }


    public Element generate_random_performers(Document doc){
        Element performers = doc.createElement("performers");
        int number_perfomer = r.nextInt(number_elements)+1;
        for(int i = 0; i < number_perfomer; i++){
            Element performer = doc.createElement("performer");
            performer.setAttribute("name", "name_" + i);
            performer.setAttribute("type", "type_" + i);
            performers.appendChild(performer);
        }
        return performers;
    }

    public Element generate_random_performance(Document doc){
        return doc.createElement("performance");
    }

    public Element generate_random_percussion_special(Document doc){
        String[] type = ("play_on_border,stop_drumhead,muffle_with_harmonics,muffle,rub,shake").split(",");
        Element percussion_special = doc.createElement("percussion_special");
        if(isGenerateElement()) percussion_special.setAttribute("id", id_generator());
        percussion_special.setAttribute("type", type[r.nextInt(type.length)]);
        if(events.size() > 0)
            if(isGenerateElement()) percussion_special.setAttribute("event_ref", events.get(r.nextInt(events.size())));
        return percussion_special;
    }

    public Element generate_random_percussion_beater(Document doc) {
        String[] type = ("bow,snare_stick,snare_stick_plastic,spoon_shaped,guiro,triangle,knitting_needle,hand,hammer,metal_hammer,wooden_timpani_mallet,light_timpani_mallet,medium_timpani_mallet,heavy_timpani_mallet,light_vibraphone_mallet,medium_vibraphone_mallet,heavy_vibraphone_mallet,light_bassdrum_mallet,medium_bassdrum_mallet,heavy_bassdrum_mallet,steel_core_bassdrum_mallet,coin,brush,nails").split(",");
        Element percussion_beater = doc.createElement("percussion_beater");
        if (isGenerateElement()) percussion_beater.setAttribute("id", id_generator());
        if (events.size() > 0){
            percussion_beater.setAttribute("start_event_ref", events.get(r.nextInt(events.size())));
            if (isGenerateElement())
                percussion_beater.setAttribute("end_event_ref", events.get(r.nextInt(events.size())));
        }
        percussion_beater.setAttribute("type", type[r.nextInt(type.length)]);
        return percussion_beater;
    }

    public Element generate_random_pedal_end(Document doc) {
        Element pedal_end = doc.createElement("pedal_end");
        if (isGenerateElement()) pedal_end.setAttribute("id", id_generator());
        if (events.size() > 0)
            pedal_end.setAttribute("event_ref", events.get(r.nextInt(events.size())));
        return pedal_end;
    }

    public Element generate_random_pedal_start(Document doc){
        Element pedal_start = doc.createElement("pedal_start");
        if(isGenerateElement()) pedal_start.setAttribute("id", id_generator());
        if (events.size() > 0)
            pedal_start.setAttribute("event_ref", events.get(r.nextInt(events.size())));
        return pedal_start;
    }

    public Element generate_random_part(Document doc){
        //String[] pitch = {"A", "B", "C", "D", "E", "F", "G"};
        String[] accidental = {"none", "double_flat", "flat_and_a_half", "flat", "demiflat", "natural", "demisharp", "sharp", "sharp_and_a_half", "double_sharp"};
        Element part = doc.createElement("part");
        part.setAttribute("id", "part_" + id_generator());
        if(isGenerateElement()) part.setAttribute("transposition_pitch", configuration.generate_note_with_probability());
        if(isGenerateElement()) part.setAttribute("transposition_accidental", accidental[r.nextInt(accidental.length)]);
        if(isGenerateElement()) part.setAttribute("performers_number", "unknown");
        else part.setAttribute("performers_number", String.valueOf(r.nextInt(20)));
        part.setAttribute("octave_offset", String.valueOf(r.nextInt(9 + 8) - 8)); // da -8 ad 8
        return part;
    }

    public Element generate_random_page(Document doc){
        Element page = doc.createElement("page");
        page.setAttribute("id", id_generator());
        if(isGenerateElement()) page.setAttribute("number", String.valueOf(r.nextInt(100)));
        return page;
    }

    public Element generate_random_other_title(Document doc){
        return doc.createElement("other_title");
    }

    public Element generate_random_ornaments(Document doc){
        return doc.createElement("ornaments");
    }

    public Element generate_random_octave_bracket(Document doc){
        Element octave_brackets = doc.createElement("octave_brackets");
        String[] type = {"8va", "8vb", "15ma", "15mb"};
        if(isGenerateElement()) octave_brackets.setAttribute("id", id_generator());
        if(isGenerateElement()) octave_brackets.setAttribute("style", type[r.nextInt(staffs.size())]);
        if (staffs.size() > 0)
            if(isGenerateElement()) octave_brackets.setAttribute("staff_ref", staffs.get(r.nextInt(staffs.size())));
        octave_brackets.setAttribute("type", type[r.nextInt(type.length)]);
        if (events.size() > 0) {
            octave_brackets.setAttribute("start_event_ref", events.get(r.nextInt(events.size())));
            octave_brackets.setAttribute("end_event_ref", events.get(r.nextInt(events.size())));
        }
        return octave_brackets;
    }

    public Element generate_random_number(Document doc){
        return doc.createElement("number");
    }

    public Element generate_random_notes(Document doc){
        return doc.createElement("notes");
    }

    public Element generate_random_notehead(Document doc){
        Element notehead = doc.createElement("notehead");

        String[] style = {"normal", "harmonic", "unpitched", "cymbal", "parenthesis", "circled", "squared"};
        if(isGenerateElement()) notehead.setAttribute("id", "id_" + id_generator());
        if(isGenerateElement()) notehead.setAttribute("style", style[r.nextInt(style.length)]);
        if (staffs.size() > 0)
            if(isGenerateElement()) notehead.setAttribute("staff_ref", staffs.get(r.nextInt(staffs.size())));
        return notehead;
    }

    public Element generate_random_notational(Document doc){
        return doc.createElement("notational");
    }

    public Element generate_random_natation_instance_group(Document doc){
        Element notation_instance_group = doc.createElement("notation_instance_group");

        notation_instance_group.setAttribute("description", "description_" + r.nextInt(50));
        return notation_instance_group;
    }

    public Element generate_random_notation_instance(Document doc){
        Element notation_instance = doc.createElement("notation_instance");
        String[] measurement_unit = {"centimeters", "millimeters", "inches", "decimal_inches", "points", "picas", "pixels", "twips"};

        notation_instance.setAttribute("description", "description_" + r.nextInt(50));
        notation_instance.setAttribute("format", formats[r.nextInt(formats.length)]);
        notation_instance.setAttribute("measurement_unit", measurement_unit[r.nextInt(measurement_unit.length)]);
        notation_instance.setAttribute("file_name", "file_" + r.nextInt(100));
        notation_instance.setAttribute("position_in_group", String.valueOf(r.nextInt(100)));
        return notation_instance;
    }

    public Element generate_random_notation_event(Document doc){
        Element notation_event = doc.createElement("notation_event");
        if (events.size() > 0)
            notation_event.setAttribute("event_ref", events.get(r.nextInt(events.size())));
        notation_event.setAttribute("description", "description_" + r.nextInt(50));
        notation_event.setAttribute("start_position", String.valueOf(r.nextInt(50)));
        notation_event.setAttribute("end_position", String.valueOf(r.nextInt(50)));
        return notation_event;
    }

    public Element generate_random_articulation(Document doc){
        Element articulation = doc.createElement("articulation");

        Element[] elements = {doc.createElement("custom_articulation"), doc.createElement("normal_accent"),
                doc.createElement("staccato"), doc.createElement("staccatissimo"), doc.createElement("strong_accent"),
                doc.createElement("tenuto"), doc.createElement("stopped_note"), doc.createElement("snap_pizzicato"),
                doc.createElement("natural_harmonic"), doc.createElement("up_bow"), doc.createElement("down_bow"),
                doc.createElement("open_mute"), doc.createElement("close_mute")};

        int number_of_articulation = r.nextInt(20);
        for(int j = 0; j < number_of_articulation; j++) articulation.appendChild(elements[r.nextInt(elements.length)]);
        return articulation;

    }

    public Element generate_random_simple_description(Document doc){
        return doc.createElement("simple_description");
    }

    public Element generate_random_music_font(Document doc){
        return doc.createElement("music_font");
    }

    public Element generate_random_multiple_rest(Document doc){

        Element multiple_rest = doc.createElement("multiple_rest");
        if(events.size()>0)
            if(isGenerateElement()) multiple_rest.setAttribute("event_ref", events.get(r.nextInt(events.size())));
        multiple_rest.setAttribute("number_of_measures", String.valueOf(r.nextInt(50)));
        return multiple_rest;
    }


    public Element generate_random_multiple_ending(Document doc){
        String id = id_generator();
        Element multiple_endings = doc.createElement("multiple_endigs");
        if(isGenerateElement()) multiple_endings.setAttribute("id", String.valueOf(id));

        int number_multiple_ending = r.nextInt(number_elements);
        for(int i = 0; i < number_multiple_ending; i++) {
            Element multiple_ending = doc.createElement("multiple_ending");
            if(isGenerateElement()) multiple_ending.setAttribute("id", "id_" + id + "_" + i);
            multiple_ending.setAttribute("number", String.valueOf(r.nextInt(30)));
            multiple_ending.setAttribute("return_to", events.get(r.nextInt(events.size())));
            if (events.size() > 0) {
                multiple_ending.setAttribute("start_event_ref", events.get(r.nextInt(events.size())));
                multiple_ending.setAttribute("end_event_ref", events.get(r.nextInt(events.size())));
            }
            multiple_endings.appendChild(multiple_ending);
        }
        return multiple_endings;
    }

    public Element generate_random_mpeg4_spine_ref(Document doc){
        Element mpeg4_spine_ref = doc.createElement("mpeg4_spine_ref");
        if (events.size() > 0)
            mpeg4_spine_ref.setAttribute("event_ref", events.get(new Random().nextInt(events.size())));
        return mpeg4_spine_ref;
    }


    public Element generate_random_mpeg4_spine_event(Document doc){
        Element mpeg4_spine_event = doc.createElement("mpeg4_spine_event");
        if (events.size() > 0)
            mpeg4_spine_event.setAttribute("event_ref", events.get(new Random().nextInt(events.size())));
        mpeg4_spine_event.setAttribute("line_number", String.valueOf(new Random().nextInt(50)));
        return mpeg4_spine_event;
    }

    public Element generate_random_mpeg4score(Document doc){
        Element mpeg4score = doc.createElement("mpeg4_score");

        mpeg4score.setAttribute("file_name", "file_" + r.nextInt(100));
        return mpeg4score;
    }

    public Element generate_random_mpeg4_orchestra(Document doc){

        Element mpeg4_orchestra = doc.createElement("mpeg4_orchestra");
        mpeg4_orchestra.setAttribute("file_name", "file_" + r.nextInt(100));
        return mpeg4_orchestra;
    }

    public Element generate_random_mpeg4_instrument_mapping(Document doc){

        Element mpeg4_instrument_mapping = doc.createElement("mpeg4_instrument_mapping");
        mpeg4_instrument_mapping.setAttribute("instrument_name", "instrument_" + r.nextInt(100));
        if(isGenerateElement()) mpeg4_instrument_mapping.setAttribute("start_line", String.valueOf(r.nextInt(50)));
        if(isGenerateElement()) mpeg4_instrument_mapping.setAttribute("end_line", String.valueOf(r.nextInt(50)));
        if(isGenerateElement()) mpeg4_instrument_mapping.setAttribute("pnml_file", "file_" + r.nextInt(50));
        return mpeg4_instrument_mapping;
    }

    public Element generate_random_mpeg4_part_ref(Document doc){
        Element mpeg4_part_ref = doc.createElement("mpeg4_part_ref");
        if (parts.size() > 0)
            mpeg4_part_ref.setAttribute("part_ref", parts.get(new Random().nextInt(parts.size())));
        return mpeg4_part_ref;
    }

    public Element generate_random_mpeg4_instance(Document doc){
        return doc.createElement("mpeg4_instance");
    }

    public Element generate_random_mordent(Document doc){
        String[] style = {"normal", "double-rhomb", "up_hook", "down_hook"};
        String[] type = {"upper", "lower"};
        String[] length = {"normal", "double"};
        String[] accidental = {"none", "double_flat", "flat_and_a_half", "flat", "demiflat", "natural", "demisharp", "sharp", "sharp_and_a_half", "double_sharp"};

        Element mordent = doc.createElement("mordent");
        if (events.size() > 0)
            mordent.setAttribute("event_ref", events.get(r.nextInt(events.size())));
        mordent.setAttribute("type", type[r.nextInt(type.length)]);
        mordent.setAttribute("length", length[r.nextInt(length.length)]);
        mordent.setAttribute("accidental", accidental[r.nextInt(accidental.length)]);
        mordent.setAttribute("style", style[r.nextInt(style.length)]);
        return  mordent;
    }

    public Element generate_random_mir_subobject(Document doc){
        Element mir_subobject = doc.createElement("mir_subobject");
        if(isGenerateElement()) mir_subobject.setAttribute("id", "msub_" + id_generator());
        if(isGenerateElement()) mir_subobject.setAttribute("description", "description_" + r.nextInt(100));
        if(isGenerateElement()) mir_subobject.setAttribute("displacement_ref", "displacement_" + r.nextInt(100));
        if (segments.size() > 0)
            mir_subobject.setAttribute("segment_ref", segments.get(r.nextInt(segments.size())));
        return mir_subobject;
    }

    public Element generate_random_mir_object(Document doc){

        Element mir_object = doc.createElement("mir_object");
        if(isGenerateElement()) mir_object.setAttribute("id", "mir_object_" + id_generator());
        if(isGenerateElement()) mir_object.setAttribute("description", "description_" + r.nextInt(100));
        if(isGenerateElement()) mir_object.setAttribute("displacement_ref", "displacement_" + r.nextInt(100));
        return mir_object;
    }

    public Element generate_random_mir_morphism(Document doc){

        Element mir_morphism = doc.createElement("mir_morphism");
        if(isGenerateElement()) mir_morphism.setAttribute("id", "mir_morphism_" + id_generator());
        if(isGenerateElement()) mir_morphism.setAttribute("description", "description_" + r.nextInt(100));

        if(domains.size() > 0) {
            mir_morphism.setAttribute("domain_ref", domains.get(r.nextInt(domains.size())));
        }
        if(codomains.size() > 0) {
            mir_morphism.setAttribute("codomain_ref", codomains.get(r.nextInt(codomains.size())));
        }
        if(isGenerateElement()) mir_morphism.setAttribute("displacement_ref", "displacement_" + r.nextInt(100));
        return mir_morphism;
    }

    public Element generate_random_mir_model(Document doc){

        Element mir_model = doc.createElement("mir_model");
        if(isGenerateElement()) mir_model.setAttribute("id", "id_" + id_generator());
        if(isGenerateElement()) mir_model.setAttribute("description", "description_" + r.nextInt(100));
        if(isGenerateElement()) mir_model.setAttribute("file_name", "file_" + r.nextInt(100));
        return mir_model;
    }

    public Element generate_random_mir_feature(Document doc){

        Element mir_feature = doc.createElement("mir_feature");
        mir_feature.setAttribute("id", "mir_feature_" + id_generator());
        if(isGenerateElement()) mir_feature.setAttribute("description", "description_" + r.nextInt(100));
        if(isGenerateElement()) mir_feature.setAttribute("displacement_ref", "displacement_" + r.nextInt(100));
        return mir_feature;
    }

    public Element generate_random_mir(Document doc){
        return doc.createElement("mir");
    }

    public Element generate_random_midi_mapping(Document doc){
        Element midi_mapping = doc.createElement("midi_mapping");
        if (parts.size() > 0)
            midi_mapping.setAttribute("part_ref", parts.get(r.nextInt(parts.size())));
        if (voice_items.size() > 0)
            midi_mapping.setAttribute("voice_ref", voice_items.get(r.nextInt(voice_items.size())));
        else
            midi_mapping.setAttribute("voice_ref", events.get(r.nextInt(events.size())));
        midi_mapping.setAttribute("track", "track_" + r.nextInt(100));
        midi_mapping.setAttribute("channel", "channel_" + r.nextInt(100));
        return midi_mapping;
    }


    public Element generate_random_midi_instance(Document doc){
        Element midi_instance = doc.createElement("midi_instance");

        midi_instance.setAttribute("file_name", "file_" + r.nextInt(100));
        String[] format = {"0", "1", "2"};
        midi_instance.setAttribute("format", format[r.nextInt(format.length)]);
        return  midi_instance;
    }

    public Element generate_random_midi_event_sequence(Document doc) {
        Element midi_event_sequence = doc.createElement("midi_event_sequence");
        String[] type = {"metrical", "timecode"};
        String[] unit = {"ticks", "sec"};

        midi_event_sequence.setAttribute("division_value", String.valueOf(r.nextFloat()).substring(0, 5));
        midi_event_sequence.setAttribute("division_type", type[r.nextInt(type.length)]);
        midi_event_sequence.setAttribute("measurement_unit", unit[r.nextInt(unit.length)]);
        return midi_event_sequence;
    }

    public Element generate_random_midi_event(Document doc){
        Element midi_event = doc.createElement("midi_event");
        if (events.size() > 0)
            midi_event.setAttribute("event_ref", events.get(r.nextInt(events.size())));
        midi_event.setAttribute("timing", "timing_" + r.nextInt(100));
        return midi_event;
    }

    public Element generate_random_metronomic_indication(Document doc){
        String[] duration = configuration.generate_duration();
        Element metronomic_indication = doc.createElement("metronomic_indication");
        metronomic_indication.setAttribute("num", duration[0]);
        metronomic_indication.setAttribute("den", duration[1]);
        metronomic_indication.setAttribute("value", String.valueOf(r.nextInt(200)));
        metronomic_indication.setAttribute("dots", String.valueOf(r.nextInt(200)));
        if (events.size() > 0)
            metronomic_indication.setAttribute("event_ref", events.get(r.nextInt(events.size())));
        return metronomic_indication;
    }

    public Element generate_random_measure_repeat(Document doc) {
        Element measure_repeat = doc.createElement("measure_repeat");

        if (isGenerateElement()) {
            if (events.size() > 0)
                measure_repeat.setAttribute("event_ref", events.get(r.nextInt(events.size())));
        }
        measure_repeat.setAttribute("number_of_measures", String.valueOf(r.nextInt(100)));
        return measure_repeat;
    }

    public Element generate_random_measure(Document doc){
        Element measure = doc.createElement("measure");
        if(isGenerateElement()) measure.setAttribute("id", "measure_" + id_generator());

        measure.setAttribute("number", String.valueOf(r.nextInt(100)));
        String[] show = {"yes", "no"};
        String[] style = {"arabic_numbers", "roman_numbers", "small_letters", "capital_letters"};
        if(isGenerateElement()) measure.setAttribute("show_number", show[r.nextInt(show.length)]);
        if(isGenerateElement()) measure.setAttribute("numbering_style", style[r.nextInt(style.length)]);
        return measure;
    }

    public Element generate_random_main_title(Document doc){
        Element main_title = doc.createElement("main_title");
        main_title.setTextContent("title_" + new Random().nextInt(20));
        return main_title;
    }

    public Element generate_random_lyrics(Document doc){
        Element lyrics = doc.createElement("lyrics");

        if (parts.size() > 0) lyrics.setAttribute("part_ref", parts.get(r.nextInt(parts.size())));
        if (voice_items.size() > 0) lyrics.setAttribute("voice_ref", voice_items.get(r.nextInt(voice_items.size())));

        for(int i = 0; i < r.nextInt(number_elements)+1; i++){
            lyrics.appendChild(generate_random_syllable(doc));
        }
        return lyrics;
    }

    public Element generate_random_los(Document doc){
        return doc.createElement("los");
    }

    public Element generate_random_logic(Document doc){
        return doc.createElement("logic");
    }

    public Element generate_random_layout(Document doc){
        String[] measurement_unit = {"centimeters", "millimeters", "inches", "decimal_inches", "points", "picas", "pixels", "twips"};

        Element layout = doc.createElement("layout");
        layout.setAttribute("hpos_per_unit", String.valueOf(r.nextInt(20)));
        layout.setAttribute("measurement_unit", measurement_unit[r.nextInt(measurement_unit.length)]);
        return layout;
    }

    public Element generate_random_layout_system(Document doc){
        Element layout_system = doc.createElement("layout_system");
        if(isGenerateElement()) layout_system.setAttribute("id", id_generator());

        layout_system.setAttribute("upper_left_x", String.valueOf(r.nextInt(20)));
        layout_system.setAttribute("upper_left_y", String.valueOf(r.nextInt(20)));
        layout_system.setAttribute("lower_right_x", String.valueOf(r.nextInt(20)));
        layout_system.setAttribute("lower_right_y", String.valueOf(r.nextInt(20)));
        return layout_system;
    }

    public Element generate_random_layout_staff(Document doc){
        Element layout_staff = doc.createElement("layout_staff");
        if(isGenerateElement()) layout_staff.setAttribute("id", "layout_staff_" + id_generator());
        String[] values = {"yes", "no"};

        if (staffs.size() > 0)
            layout_staff.setAttribute("staff_ref", staffs.get(new Random().nextInt(staffs.size())));
        layout_staff.setAttribute("vertical_offset", String.valueOf(r.nextInt(20)));
        layout_staff.setAttribute("height", String.valueOf(r.nextInt(20)));
        layout_staff.setAttribute("ossia", values[r.nextInt(values.length)]);
        layout_staff.setAttribute("show_time_signature", values[r.nextInt(values.length)]);
        layout_staff.setAttribute("show_clef", values[r.nextInt(values.length)]);
        layout_staff.setAttribute("show_key_signature", values[r.nextInt(values.length)]);
        return layout_staff;
    }

    public Element generate_random_layout_shapes(Document doc) {
        Element layout_shapes = doc.createElement("layout_shapes");

        layout_shapes.setAttribute("horizontal_offset", String.valueOf(r.nextInt(20)));
        layout_shapes.setAttribute("vertical_offset", String.valueOf(r.nextInt(20)));
        return layout_shapes;
    }

    public Element generate_random_layout_images(Document doc) {
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

    public Element generate_random_custom_key_signature(Document doc){
        String[] accidental = {"none", "double_flat", "flat_and_a_half", "flat", "demiflat", "natural", "demisharp", "sharp", "sharp_and_a_half", "double_sharp"};
        Element custom_key_signature = doc.createElement("custom_key_signature");
        if (events.size() > 0)
            custom_key_signature.setAttribute("event_ref", events.get(new Random().nextInt(events.size())));

        int number_key_accidental = r.nextInt(number_elements)+1;
        //String[] steps = {"A", "B", "C", "D", "E", "F", "G"};
        for(int i = 0; i < number_key_accidental; i++){
            Element key_accidental = doc.createElement("key_accidental");
            key_accidental.setAttribute("step", configuration.generate_note_with_probability());
            key_accidental.setAttribute("accidental", accidental[r.nextInt(accidental.length)]);
            custom_key_signature.appendChild(key_accidental);
        }
        return custom_key_signature;
    }

    public Element generate_random_key_signature(Document doc){
        Element key_signature = doc.createElement("key_signature");

        if (events.size() > 0)
            key_signature.setAttribute("event_ref", events.get(new Random().nextInt(events.size())));
        Element num;
        if(isGenerateElement()) num = doc.createElement("sharp_num");
        else num = doc.createElement("flat_num");
        String[] number = {"0", "1", "2", "3", "4", "5", "6", "7"};
        num.setAttribute("number", number[r.nextInt(number.length)]);
        key_signature.appendChild(num);

        return key_signature;
    }

    public Element generate_random_key(Document doc){
        Element key = doc.createElement("key");
        if(isGenerateElement()) key.setAttribute("id", "key_" + id_generator());
        if (staffs.size() > 0)
            if(isGenerateElement()) key.setAttribute("staff_ref", staffs.get(new Random().nextInt(staffs.size())));
        return key;
    }

    public Element generate_random_jump_to(Document doc){
        Element jump_to = doc.createElement("jump_to");
        if(isGenerateElement()) jump_to.setAttribute("id", id_generator());
        jump_to.setAttribute("event_ref", events.get(new Random().nextInt(events.size())));
        return jump_to;
    }

    public Element generate_random_horizontal_symbols(Document doc){
        return doc.createElement("horizontal_symbols");
    }

    public Element generate_random_hairpin(Document doc){
        Element hairpin = doc.createElement("hairpine");

        if(isGenerateElement()) hairpin.setAttribute("id", id_generator());
        if (events.size() > 0) {
            hairpin.setAttribute("start_event_ref", events.get(r.nextInt(events.size())));
            hairpin.setAttribute("end_event_ref", events.get(r.nextInt(events.size())));
        }
        if (staffs.size() > 0)
            if(isGenerateElement()) hairpin.setAttribute("staff_ref", staffs.get(r.nextInt(staffs.size())));
        String[] type = {"crescendo", "diminuendo"};
        hairpin.setAttribute("type", type[r.nextInt(type.length)]);
        return hairpin;
    }


    public Element generate_random_gregorian_symbol(Document doc){
        Element gregorian_symbol = doc.createElement("gregorian_symbol");

        String[] neume = ("punctum,virga,punctum_inclinatum,quilisma,apostrofa,oriscus,podatus,pes,clivis,flexa,epiphonus,cephalicus,bistropha,bivirga,trigon,torculus,porrectus,scandicus,salicus,climacus,tristropha,trivirga,strophicus,pressus,custos").split(",");

        String[] subtripunctis = ("no,praepunctis,subpunctis,subbipunctis,subtripunctis,subquadripunctis,subquinquipunctis").split(",");
        String[] interpretative_mark = ("no,vertical_episema,horizontal_episema,liquescens").split(",");
        String[] mora = {"yes", "no"};
        String[] inflextion = ("no,resupinus,flexus").split(",");
        if(isGenerateElement()) gregorian_symbol.setAttribute("id", "symbol_" + id_generator());
        if (events.size() > 0)
            gregorian_symbol.setAttribute("event_ref", events.get(r.nextInt(events.size())));
        gregorian_symbol.setAttribute("neume", neume[r.nextInt(neume.length)]);
        gregorian_symbol.setAttribute("subpunctis", subtripunctis[r.nextInt(subtripunctis.length)]);
        gregorian_symbol.setAttribute("interpretative_mark", interpretative_mark[r.nextInt(interpretative_mark.length)]);
        gregorian_symbol.setAttribute("mora", mora[r.nextInt(mora.length)]);
        gregorian_symbol.setAttribute("inflexion", inflextion[r.nextInt(inflextion.length)]);
        return gregorian_symbol;
    }

    public Element generate_random_graphic_instance_group(Document doc){
        Element graphic_instance_group = doc.createElement("graphic_instance_group");
        graphic_instance_group.setAttribute("description", "description_" + new Random().nextInt(30));
        return  graphic_instance_group;
    }

    public Element generate_random_graphic_instance(Document doc){

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


    public Element generate_random_graphic_event(Document doc){
        Element graphic_event = doc.createElement("graphic_event");

        if (events.size() > 0)
            graphic_event.setAttribute("event_ref", events.get(r.nextInt(events.size())));
        graphic_event.setAttribute("upper_left_x", String.valueOf(r.nextInt(20)));
        graphic_event.setAttribute("upper_left_y", String.valueOf(r.nextInt(20)));
        graphic_event.setAttribute("lower_right_x", String.valueOf(r.nextInt(20)));
        graphic_event.setAttribute("lower_right_y", String.valueOf(r.nextInt(20)));
        if(isGenerateElement())  graphic_event.setAttribute("highlight_color", "color_" + r.nextInt(255));
        if(isGenerateElement()) graphic_event.setAttribute("description", "description" + r.nextInt(20));
        return graphic_event;
    }

    public Element generate_random_glissando(Document doc){
        Element glissando = doc.createElement("glissando");
        if(isGenerateElement()) glissando.setAttribute("id", id_generator());
        if (events.size() > 0) {
            glissando.setAttribute("start_event_ref", events.get(r.nextInt(events.size())));
            if (isGenerateElement()) glissando.setAttribute("end_event_ref", events.get(r.nextInt(events.size())));
        }
        return glissando;
    }

    public Element generate_random_genres(Document doc){

        Element genres = doc.createElement("genres");
        int number_genre = r.nextInt(number_elements)+1;
        for(int i = 0; i < number_genre; i++) {
            Element genre = doc.createElement("genre");
            genre.setAttribute("name", "name_" + r.nextInt(20));
            if (isGenerateElement()) genre.setAttribute("description", "description_" + r.nextInt(20));
            if (isGenerateElement()) genre.setAttribute("weight", "weight_" + r.nextInt(20));
            genres.appendChild(genre);
        }
        return genres;
    }

    public Element generate_random_general(Document doc){
        return doc.createElement("general");
    }

    public Element generate_random_fingering(Document doc){
        Element fingering = doc.createElement("fingering");
        String[] number = {"1", "2", "3", "4", "5"};
        fingering.setAttribute("number", number[new Random().nextInt(number.length)]);
        return  fingering;
    }

    public Element generate_random_fermata(Document doc){
        Element fermata = doc.createElement("fermata");

        if(isGenerateElement()) fermata.setAttribute("id", id_generator());

        fermata.setAttribute("event_ref", events.get(r.nextInt(events.size())));

        return fermata;
    }


    public Element generate_feature_object(Document doc){

        Element feature_object = doc.createElement("feature_object");
        if(isGenerateElement()) feature_object.setAttribute("id", "feature_object_" + id_generator());
        feature_object.setAttribute("name", "name_" + r.nextInt(50));
        return feature_object;
    }

    public Element generate_random_event(Document doc){

        Element event = doc.createElement("event");
        event.setAttribute("id", "event" + id_generator());
        event.setAttribute("timing", String.valueOf(r.nextInt(256)));
        event.setAttribute("hpos", String.valueOf(r.nextInt(256)));
        return event;
    }

    public Element generate_random_end(Document doc){
        Element end = doc.createElement("ref");

        if(isGenerateElement()) end.setAttribute("id", id_generator());
        end.setAttribute("event_ref", events.get(r.nextInt(events.size())));
        return end;
    }

    public Element generate_random_dynamic(Document doc){

        String[] extension_line_shape = {"normal", "dotted", "dashed"};
        Element dynamic = doc.createElement("dynamic");
        dynamic.setAttribute("id", id_generator());
        if (events.size() > 0)
            if(isGenerateElement()) dynamic.setAttribute("extension_line_to", events.get(r.nextInt(events.size())));
        if (staffs.size() > 0)
            if(isGenerateElement()) dynamic.setAttribute("staff_ref", staffs.get(r.nextInt(staffs.size())));
        dynamic.setAttribute("event_ref", events.get(r.nextInt(events.size())));
        if(isGenerateElement()) dynamic.setAttribute("extension_line_shape", extension_line_shape[r.nextInt(extension_line_shape.length)]);
        return dynamic;
    }

    public Element generate_random_duration(Document doc){
        String[] dur = configuration.generate_duration();
        Element duration = doc.createElement("duration");
        duration.setAttribute("num", dur[0]);
        duration.setAttribute("den", dur[1]);
        if(isGenerateElement()){
            Element tuplet_ratio = doc.createElement("tuplet_ratio");
            dur = configuration.generate_duration();
            tuplet_ratio.setAttribute("enter_num", dur[0]);
            tuplet_ratio.setAttribute("enter_den", dur[1]);
            dur = configuration.generate_duration();
            tuplet_ratio.setAttribute("in_num", dur[0]);
            tuplet_ratio.setAttribute("in_den", dur[1]);
            if(isGenerateElement()) tuplet_ratio.setAttribute("in_dots", String.valueOf(r.nextInt(5)));
            duration.appendChild(tuplet_ratio);
        }
        return duration;
    }

    public Element generate_random_description(Document doc){
        return doc.createElement("description");
    }

    public Element generate_random_date(Document doc){
        Element date = doc.createElement("date");
        date.setAttribute("type", "type_" + new Random().nextInt(30));
        return date;
    }

    public Element generate_random_custom_hsymbol(Document doc){
        Element custom_hsymbol = doc.createElement("custom_hsymbol");

        if(isGenerateElement()) custom_hsymbol.setAttribute("id", id_generator());
        custom_hsymbol.setAttribute("start_event_ref", events.get(r.nextInt(events.size())));
        custom_hsymbol.setAttribute("end_event_ref", events.get(r.nextInt(events.size())));
        return custom_hsymbol;
    }

    public Element generate_random_csound_spine_ref(Document doc){

        Element csound_spine_ref = doc.createElement("csound_spine_ref");

        if (events.size() > 0) {
            csound_spine_ref.setAttribute("event_ref", events.get(r.nextInt(events.size())));
        }
        return  csound_spine_ref;
    }

    public Element generate_random_csound_spine_event(Document doc){

        Element csound_spine_event = doc.createElement("csound_spine_event");

        if (events.size() > 0) {
            csound_spine_event.setAttribute("event_ref", events.get(r.nextInt(events.size())));
        }
        csound_spine_event.setAttribute("line_number", String.valueOf(r.nextInt(40)));
        return  csound_spine_event;
    }

    public Element generate_random_csound_score(Document doc){
        Element csound_score = doc.createElement("csound_score");
        csound_score.setAttribute("file_name", "file_" + r.nextInt(100));
        return csound_score;
    }

    public Element generate_random_csound_part_ref(Document doc){
        Element csound_part_ref = doc.createElement("csound_part_ref");
        if (parts.size() > 0)
            csound_part_ref.setAttribute("part_ref", parts.get(new Random().nextInt(parts.size())));
        return csound_part_ref;
    }

    public Element generate_random_csound_orchestra(Document doc){
        Element csound_orchestra = doc.createElement("csound_orchestra");
        csound_orchestra.setAttribute("file_name", "file_" + new Random().nextInt(50));
        return csound_orchestra;
    }

    public Element generate_random_csound_instrument_mapping(Document doc){

        Element csound_instrument_mapping = doc.createElement("csound_instrument_mapping");
        csound_instrument_mapping.setAttribute("instrument_number", String.valueOf(r.nextInt(20)));
        if(isGenerateElement()) csound_instrument_mapping.setAttribute("start_line", String.valueOf(r.nextInt(50)));
        if(isGenerateElement()) csound_instrument_mapping.setAttribute("end_line", String.valueOf(r.nextInt(50)));
        if(isGenerateElement()) csound_instrument_mapping.setAttribute("pnml_file", "file_" + String.valueOf(r.nextInt(50)));
        return csound_instrument_mapping;

    }
    public Element generate_random_csound_instance(Document doc){
        return doc.createElement("csound_instance");
    }

    public Element generate_random_fine(Document doc){
        Element fine = doc.createElement("fine");

        if (events.size() > 0) {
            fine.setAttribute("start_event_ref", events.get(r.nextInt(events.size())));
            fine.setAttribute("end_event_ref", events.get(r.nextInt(events.size())));
        }
        if(isGenerateElement()) fine.setAttribute("id", id_generator());
        return fine;
    }

    public Element generate_random_segno(Document doc) {
        Element segno = doc.createElement("segno");

        if (events.size() > 0){
            segno.setAttribute("start_event_ref", events.get(r.nextInt(events.size())));
            segno.setAttribute("end_event_ref", events.get(r.nextInt(events.size())));
        }
        if(isGenerateElement()) segno.setAttribute("id", id_generator());
        return segno;
    }

    public Element generate_random_coda(Document doc) {
        Element coda = doc.createElement("coda");

        if (events.size() > 0){
            coda.setAttribute("start_event_ref", events.get(r.nextInt(events.size())));
            coda.setAttribute("end_event_ref", events.get(r.nextInt(events.size())));
        }
        if(isGenerateElement()) coda.setAttribute("id", id_generator());
        return coda;
    }

    public Element generate_random_clef(Document doc){
        Element clef = doc.createElement("clef");

        if (events.size() > 0) {
            clef.setAttribute("event_ref", events.get(r.nextInt(events.size())));
        }
        clef.setAttribute("staff_step", String.valueOf(r.nextInt(20)));
        String[] shape = {"G", "F", "C", "gregorian_F", "gregorian_C", "percussion", "doubleG", "tabguitar"};
        String[] octave_num = {"0", "8", "-8", "15"};
        clef.setAttribute("shape", shape[r.nextInt(shape.length)]);
        clef.setAttribute("octave_num", octave_num[r.nextInt(octave_num.length)]);
        return clef;
    }

    public Element generate_random_chord(Document doc){
        String[] stem_direction = {"up", "down", "none"};
        String[] beam_and_cue = {"yes", "no"};
        String[] tremolo_lines = {"no", "1", "2", "3", "4", "5", "6"};

        Element chord = doc.createElement("chord");
        chord.setAttribute("id", "chord_" + id_generator());


        if (events.size() > 0)
            chord.setAttribute("event_ref", events.get(r.nextInt(events.size())));
        if(isGenerateElement())  chord.setAttribute("stem_direction", stem_direction[r.nextInt(stem_direction.length)]);
        if(isGenerateElement()) chord.setAttribute("beam_before", beam_and_cue[r.nextInt(beam_and_cue.length)]);
        if(isGenerateElement()) chord.setAttribute("beam_before", beam_and_cue[r.nextInt(beam_and_cue.length)]);
        chord.setAttribute("tremolo_lines", tremolo_lines[r.nextInt(tremolo_lines.length)]);
        return chord;
    }

    public Element generate_random_chord_symbol(Document doc) {
        Element chord_symbol = doc.createElement("chord_symbol");
        if (isGenerateElement()) chord_symbol.setAttribute("id", id_generator());
        if (events.size() > 0){
            chord_symbol.setAttribute("start_event_ref", events.get(r.nextInt(events.size())));
            chord_symbol.setAttribute("end_event_ref", events.get(r.nextInt(events.size())));
        }
        return chord_symbol;
    }

    public Element generate_random_chord_grid(Document doc){
        String id = id_generator();
        Element chord_grid = doc.createElement("chord_grid");
        if(isGenerateElement()) chord_grid.setAttribute("id", "chord_grid_" + id);
        if(isGenerateElement()) chord_grid.setAttribute("author", "author_" + r.nextInt(20));
        if(isGenerateElement()) chord_grid.setAttribute("description", "description_" + r.nextInt(20));
        int number_chord_name = r.nextInt(number_elements)+1;
        for(int i = 0; i < number_chord_name; i++){
            Element chord_name = doc.createElement("chord_name");
            chord_name.setAttribute("root_id", events.get(r.nextInt(events.size())));
            chord_grid.appendChild(chord_name);
        }
        return chord_grid;
    }

    public Element generate_random_breath_mark(Document doc){
        Element breath_mark = doc.createElement("breath_mark");
        breath_mark.setAttribute("id", id_generator());
        if (staffs.size() > 0)
            if(isGenerateElement()) breath_mark.setAttribute("staff_ref", staffs.get(r.nextInt(staffs.size())));

        if (events.size() > 0) {
            breath_mark.setAttribute("start_event_ref", events.get(r.nextInt(events.size())));
            breath_mark.setAttribute("end_event_ref", events.get(r.nextInt(events.size())));
        }
        String[] type = {"comma", "caesura"};
        breath_mark.setAttribute("type", type[r.nextInt(type.length)]);
        return breath_mark;
    }

    public Element generate_random_brackets(Document doc){
        Element brackets = doc.createElement("brackets");
        String[] marker = {"start_of_staff_group", "end_of_staff_group"};
        String[] shape = {"square", "rounded_square", "brace", "vertical_bar", "none"};
        brackets.setAttribute("marker", marker[r.nextInt(marker.length)]);
        brackets.setAttribute("group_number", "group_" + r.nextInt(20));
        brackets.setAttribute("shape", shape[r.nextInt(shape.length)]);
        return brackets;
    }

    public Element generate_random_bend(Document doc){
        Element bend = doc.createElement("bend");
        String[] type = {"single", "double"};
        //String[] pitch = {"A", "B", "C", "D", "E", "F", "up", "down"};
        String[] accidental = {"none", "double_flat", "flat_and_a_half", "flat", "demiflat", "natural", "demisharp", "sharp", "sharp_and_a_half", "double_sharp"};

        if(isGenerateElement()) bend.setAttribute("id", id_generator());
        if (events.size() > 0)
            bend.setAttribute("event_ref", events.get(r.nextInt(events.size())));
        bend.setAttribute("to_pitch", configuration.generate_note_with_probability());
        bend.setAttribute("type", type[r.nextInt(type.length)]);
        if(isGenerateElement()) bend.setAttribute("to_accindental", accidental[r.nextInt(accidental.length)]);
        if(isGenerateElement()) bend.setAttribute("to_octave", String.valueOf(r.nextInt(12)));
        return bend;
    }

    public Element generate_random_barre(Document doc){
        Element barre = doc.createElement("barre");
        barre.setAttribute("start_string_position", "start_string_" + r.nextInt(20));
        barre.setAttribute("end_string_position", "end_string_" + r.nextInt(20));
        barre.setAttribute("freat_position", "freat_string_" + r.nextInt(20));
        return barre;
    }

    public Element generate_random_baroque_appoggiatura(Document doc){
        Element baroque_appoggiatura = doc.createElement("baroque_appoggiatura");

        String[] style = {"hairpin", "plus", "pipe", "double_slur", "slash", "backslash", "up_hook", "down_hook"};
        if (events.size() > 0)
            baroque_appoggiatura.setAttribute("event_ref", events.get(r.nextInt(events.size())));
        if(isGenerateElement()) baroque_appoggiatura.setAttribute("id", id_generator());
        baroque_appoggiatura.setAttribute("style", style[r.nextInt(style.length)]);
        return baroque_appoggiatura;
    }

    public Element generate_random_baroque_acciaccatura(Document doc){
        Element baroque_acconciatura = doc.createElement("baroque_acconciatura");

        String[] style = {"vertical_turn", "mordent", "flatte", "tierce_coulee", "slash", "backslash"};
        if (events.size() > 0)
            baroque_acconciatura.setAttribute("event_ref", events.get(r.nextInt(events.size())));
        if(isGenerateElement()) baroque_acconciatura.setAttribute("id", id_generator());
        baroque_acconciatura.setAttribute("style", style[r.nextInt(style.length)]);
        return baroque_acconciatura;

    }
    public Element generate_random_barline(Document doc){
        Element barline = doc.createElement("barline");

        String[] style = {"dashed", "double", "final", "invisible", "standard", "medium", "short"};
        String[] extension = {"single_staff", "staff_group", "all_staves", "mensurstrich"};
        if (events.size() > 0)
            barline.setAttribute("event_ref", events.get(r.nextInt(events.size())));
        barline.setAttribute("style", style[r.nextInt(style.length)]);
        barline.setAttribute("extension", extension[r.nextInt(extension.length)]);
        return barline;
    }

    public Element generate_random_augmentation_dots(Document doc){
        Element augmentation_dots = doc.createElement("augmentation_dots");
        augmentation_dots.setAttribute("number", String.valueOf(new Random().nextInt(100)));
        return augmentation_dots;
    }

    public Element generate_random_audio(Document doc){
        Element audio = doc.createElement("audio");
        int number_track = new Random().nextInt(number_elements);
        for(int i = 0; i < number_track; i++){
            Element track = doc.createElement("track");
            track.setAttribute("file_name", "file_" + r.nextInt(100));
            track.setAttribute("encoding_format", formats[r.nextInt(formats.length)]);
            track.setAttribute("file_format", formats[r.nextInt(formats.length)]);
            audio.appendChild(track);
        }
        return audio;
    }

    public Element generate_random_arpeggio(Document doc){
        Element arpeggio = doc.createElement("arpeggio");

        String[] shape = {"wavy", "line", "no_arpeggio"};
        String[] direction = {"up", "down"};
        arpeggio.setAttribute("shape", shape[r.nextInt(shape.length)]);
        arpeggio.setAttribute("direction", direction[r.nextInt(direction.length)]);
        return arpeggio;
    }

    public Element generate_random_notehead_ref(Document doc){

        Element notehead_ref = doc.createElement("notehead_ref");
        if (events.size() > 0)
            notehead_ref.setAttribute("event_ref", events.get(r.nextInt(events.size())));
        return notehead_ref;
    }

    public Element generate_random_appoggiatura(Document doc){
        Element appoggiatura = doc.createElement("appoggiatura");
        String[] slur = {"yes", "no"};

        Element analysis = doc.createElement("appoggiatura");
        if(isGenerateElement()) analysis.setAttribute("id", id_generator());
        appoggiatura.setAttribute("slur", slur[r.nextInt(slur.length)]);
        if (events.size() > 0)
            appoggiatura.setAttribute("event_ref", events.get(r.nextInt(events.size())));
        return appoggiatura;
    }

    public Element generate_random_analysis(Document doc){

        Element analysis = doc.createElement("analysis");
        if(isGenerateElement()) analysis.setAttribute("id", "analysis_" + id_generator());
        if(isGenerateElement()) analysis.setAttribute("author", "author_" + r.nextInt(100));
        if(isGenerateElement()) analysis.setAttribute("description", "description_" + r.nextInt(100));
        return analysis;
    }

    public Element generate_random_analog_media(Document doc){
        Element analog_media = doc.createElement("analog_media");
        return analog_media;
    }


    public Element generate_random_analog_medium(Document doc){

        Element analog_medium = doc.createElement("analog_medium");
        analog_medium.setAttribute("description", "description_" + r.nextInt(200));
        if(isGenerateElement()) analog_medium.setAttribute("copyright", "copyright_" + r.nextInt(200));
        if(isGenerateElement()) analog_medium.setAttribute("notes", "notes_" + r.nextInt(200));
        return analog_medium;
    }


    public Element generate_random_album(Document doc){

        Element album = doc.createElement("album");
        album.setAttribute("title", "album_" + r.nextInt(100));
        if(isGenerateElement()) album.setAttribute("carrier_type", "carrier_" + r.nextInt(200));
        if(isGenerateElement()) album.setAttribute("catalogue_number", String.valueOf(r.nextInt(200)));
        if(isGenerateElement()) album.setAttribute("number_of_tracks", String.valueOf(r.nextInt(200)));
        if(isGenerateElement()) album.setAttribute("publication_date", generate_date());
        if(isGenerateElement()) album.setAttribute("label", "label_" + r.nextInt(200));
        return album;
    }

    public Element generate_random_albums(Document doc){
        Element albums = doc.createElement("albums");
        return albums;
    }

    public Element generate_random_agogics(Document doc){

        Element agogics = doc.createElement("agogics");
        String[] bracketed = {"yes", "no"};
        if(isGenerateElement()) agogics.setAttribute("bracketed", bracketed[r.nextInt(bracketed.length)]);
        if (events.size() > 0)
            agogics.setAttribute("event_ref", events.get(r.nextInt(events.size())));
        return agogics;
    }

    public Element generate_random_acciaccatura(Document doc){

        String[] slur = {"yes", "no"};
        Element acciaccatura = doc.createElement("acciaccatura");

        if(isGenerateElement()) acciaccatura.setAttribute("id", id_generator());

        if (events.size() > 0)
            acciaccatura.setAttribute("event_ref", events.get(r.nextInt(events.size())));
        acciaccatura.setAttribute("slur", slur[r.nextInt(slur.length)]);

        return acciaccatura;
    }


    // vedere SaveMelody.java nel file xml.pdf
    // <pitch octave="6" step="D" actual_accidental="natural" />
    public Element generate_random_pitch(Document doc){
        Element pitch = doc.createElement("pitch");
        String octave = configuration.generate_height();
        String[] accidental = {"none", "double_flat", "flat_and_a_half", "flat", "demiflat", "natural", "demisharp", "sharp", "sharp_and_a_half", "double_sharp"};
        pitch.setAttribute("actual_accidental", accidental[r.nextInt(accidental.length)]);
        pitch.setAttribute("step", configuration.generate_note_with_probability());
        pitch.setAttribute("octave", octave);
        return pitch;
    }


  

}


