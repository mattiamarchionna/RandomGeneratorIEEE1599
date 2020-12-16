package com.random.generator;

import org.w3c.dom.*;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.Random;

public class IEEE1599Generator {
    private String path;
    private String nameOfFile;
    public Parameter configuration;
    private int number_elements;
    private ElementGenerator g_element;


    private Random r;


    public IEEE1599Generator(String path, String nameOfFile, Parameter configuration) {
        this.path = path;
        this.nameOfFile = nameOfFile;
        this.configuration = configuration;
        this.number_elements = 4;
        this.r = new Random();
        this.g_element = new ElementGenerator(configuration, this.number_elements);
    }


    public void generate_file() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();

            builder.setErrorHandler(new ErrorHandler() {
                @Override
                public void warning(SAXParseException exception){
                    System.out.println(exception);
                }

                @Override
                public void error(SAXParseException exception){
                    System.out.println(exception);
                }

                @Override
                public void fatalError(SAXParseException exception){
                    System.out.println(exception);
                }
            });

            save_xml_file(builder.newDocument(), "new"); // inizializzazione dell'XML 'vuoto'
            File file = new File(this.path + this.nameOfFile);
            Document doc = builder.parse(file);
            Element root = doc.getDocumentElement();

            generate_xml_file(root, doc);

            save_xml_file(doc, "save");
        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println("Errore nell'elaborazione del file");
            System.exit(1);
        }
    }


    public void generate_xml_file(Element root, Document doc) {

        Element general = g_element.generate_random_general(doc);
        Element logic = g_element.generate_random_logic(doc);
        Element structural = g_element.generate_random_structural(doc);
        Element notational = g_element.generate_random_notational(doc);
        Element performance = g_element.generate_random_performance(doc);
        Element audio = g_element.generate_random_audio(doc);

        // general
        append_children_to_general(general, root, doc);
        root.appendChild(general);

        // logic
        append_children_to_logic(logic, root, doc);
        root.appendChild(logic);

        if (isGenerateElement()) {
            // structural
            append_children_to_structural(doc, structural, root);
            root.appendChild(structural);
        }

        if (isGenerateElement()) {
            // notational
            append_children_to_notational(doc, notational, root);
            root.appendChild(notational);
        }

        if (isGenerateElement()) {
            // performance
            append_children_to_performance(doc, performance, root);
            root.appendChild(performance);
        }

        if (isGenerateElement()) {
            // audio
            append_children_to_audio(doc, audio, root);
            root.appendChild(audio);
        }
    }

    private void append_children_to_track_general(Document doc, Element track_general, Element root) {
        // <!ELEMENT track_general (recordings?, genres?, albums?, performers?, notes?)>
        if (isGenerateElement()) {
            Element recordings = g_element.generate_random_recordings(doc);
            for (int i = 0; i < r.nextInt(number_elements) + 1; i++)
                recordings.appendChild(g_element.generate_random_recording(doc));
            track_general.appendChild(recordings);
        }
        if (isGenerateElement()) {
            Element genres = g_element.generate_random_genres(doc);
            track_general.appendChild(genres);
        }
        if (isGenerateElement()) {
            Element albums = g_element.generate_random_albums(doc);
            for (int i = 0; i < r.nextInt(number_elements) + 1; i++) albums.appendChild(g_element.generate_random_album(doc));
            track_general.appendChild(albums);
        }
        if (isGenerateElement()) {
            Element performers = g_element.generate_random_performers(doc);
            track_general.appendChild(performers);
        }
        if (isGenerateElement()) {
            Element notes = g_element.generate_random_notes(doc);
            track_general.appendChild(notes);
        }
    }

    private void append_children_to_track_indexing(Document doc, Element track_indexing, Element root) {
        // <!ELEMENT track_indexing (track_region*, track_event+)>
        for (int i = 0; i < r.nextInt(number_elements); i++) {
            Element track_region = g_element.generate_random_track_region(doc);
            track_indexing.appendChild(track_region);
        }
        for (int i = 0; i < r.nextInt(number_elements) + 1; i++) {
            Element track_event = g_element.generate_random_track_event(doc);
            track_indexing.appendChild(track_event);
        }

    }

    private void append_children_to_track(Document doc, Element track, Element root) {
        // <!ELEMENT track (track_general?, track_indexing?, rights?)>
        if (isGenerateElement()) {
            Element track_general = g_element.generate_random_track_general(doc);
            append_children_to_track_general(doc, track_general, root);
            track.appendChild(track_general);
        }
        if (isGenerateElement()) {
            Element track_indexing = g_element.generate_random_track_indexing(doc);
            append_children_to_track_indexing(doc, track_indexing, root);
            track.appendChild(track_indexing);
        }
        if (isGenerateElement()) {
            Element rights = g_element.generate_random_rights(doc);
            track.appendChild(rights);
        }
    }

    private void append_children_to_audio(Document doc, Element audio, Element root) {
        for (int i = 0; i < r.nextInt(number_elements) + 1; i++) {
            // track
            Element track = g_element.generate_random_track(doc);
            append_children_to_track(doc, track, root);
            audio.appendChild(track);
        }
    }


    private void append_children_to_notational(Document doc, Element notational, Element root) {
        // <!ELEMENT notational (graphic_instance_group | notation_instance_group)+>
        for (int i = 0; i < r.nextInt(number_elements) + 1; i++) {
            if (isGenerateElement()) {
                // <!ELEMENT graphic_instance_group (graphic_instance+)>
                Element graphic_instance_group = g_element.generate_random_graphic_instance_group(doc);
                for (int j = 0; j < r.nextInt(number_elements) + 1; j++) {
                    // <!ELEMENT graphic_instance (graphic_event+, rights?)>
                    Element graphic_instance = g_element.generate_random_graphic_instance(doc);
                    for (int k = 0; k < r.nextInt(number_elements) + 1; k++)
                        graphic_instance.appendChild(g_element.generate_random_graphic_event(doc));
                    if (isGenerateElement()) graphic_instance.appendChild(g_element.generate_random_rights(doc));
                    graphic_instance_group.appendChild(graphic_instance);
                }
                notational.appendChild(graphic_instance_group);
            } else {
                // <!ELEMENT notation_instance_group (notation_instance+)>
                Element notation_instance_group = g_element.generate_random_natation_instance_group(doc);
                for (int j = 0; j < r.nextInt(number_elements) + 1; j++) {
                    // <!ELEMENT notation_instance (notation_event+, rights?)>
                    Element notation_instance = g_element.generate_random_notation_instance(doc);
                    for (int k = 0; k < r.nextInt(number_elements) + 1; k++) {
                        notation_instance.appendChild(g_element.generate_random_notation_event(doc));
                    }
                    if (isGenerateElement()) notation_instance.appendChild(g_element.generate_random_rights(doc));
                    notation_instance_group.appendChild(notation_instance);
                }
                notational.appendChild(notation_instance_group);
            }
        }
    }

    private void append_children_to_staff(Document doc, Element staff, Element root) {
        // <!ELEMENT staff (clef | ( key_signature | custom_key_signature) | time_signature | barline | tablature_tuning)*>
        staff.appendChild(g_element.generate_random_clef(doc));
        int indexes = r.nextInt(5);
        switch (indexes) {
            case 0:
                for (int i = 0; i < r.nextInt(number_elements); i++) staff.appendChild(g_element.generate_random_clef(doc));
                break;
            case 1:
                if (isGenerateElement()) {
                    for (int i = 0; i < r.nextInt(number_elements); i++)
                        staff.appendChild(g_element.generate_random_key_signature(doc));
                } else {
                    for (int i = 0; i < r.nextInt(number_elements); i++)
                        staff.appendChild(g_element.generate_random_custom_key_signature(doc));
                }
                break;
            case 2:
                for (int i = 0; i < r.nextInt(number_elements); i++) {
                    Element time_signature = g_element.generate_random_time_signature(doc);
                    for (int j = 0; j < r.nextInt(number_elements) + 1; j++)
                        time_signature.appendChild(g_element.generate_random_time_indication(doc));
                    staff.appendChild(time_signature);
                }
                break;
            case 3:
                for (int i = 0; i < r.nextInt(number_elements); i++) staff.appendChild(g_element.generate_random_barline(doc));
                break;
            case 4:
                for (int i = 0; i < r.nextInt(number_elements); i++)
                    staff.appendChild(g_element.generate_random_tablature_tuning(doc));
                break;
            case 5:
                break;
            default:
                System.out.println("Errore durante l'aggiunta di figli...");
        }
    }

    private void append_children_to_staff_list(Document doc, Element staff_list, Element root) {
        //if(isGenerateElement()){
        for (int i = 0; i < r.nextInt(number_elements); i++) {
            staff_list.appendChild(g_element.generate_random_brackets(doc));
        }
        //}
        //else {
        for (int i = 0; i < r.nextInt(number_elements) + 2; i++) {
            Element staff = g_element.generate_random_staff(doc);
            g_element.staffs.add(staff.getAttribute("id"));
            append_children_to_staff(doc, staff, root);
            staff_list.appendChild(staff);
        }
        //}
    }

    private void append_children_to_notehead(Document doc, Element notehead, Element root) {
        // <!ELEMENT notehead (pitch, printed_accidentals?, tie?, fingering?)>
        Element pitch = g_element.generate_random_pitch(doc);
        notehead.appendChild(pitch);
        if (isGenerateElement()) {
            Element print_accidentals = g_element.generate_random_printed_accidentals(doc);
            notehead.appendChild(print_accidentals);
        }
        if (isGenerateElement()) notehead.appendChild(g_element.generate_random_tie(doc));
        notehead.appendChild(g_element.generate_random_fingering(doc));
    }

    private void append_children_to_rest(Document doc, Element rest, Element root) {
        // duration
        Element duration = g_element.generate_random_duration(doc);
        rest.appendChild(duration);

        //argumentation_dots
        if (isGenerateElement()) {
            Element argumentation_dots = g_element.generate_random_augmentation_dots(doc);
            rest.appendChild(argumentation_dots);
        }
    }

    private void append_children_to_tablature_symbol(Document doc, Element tablature_symbol, Element root) {
        // duration
        Element duration = g_element.generate_random_duration(doc);
        tablature_symbol.appendChild(duration);

        //argumentation_dots
        if (isGenerateElement()) {
            Element argumentation_dots = g_element.generate_random_augmentation_dots(doc);
            tablature_symbol.appendChild(argumentation_dots);
        }

        if (configuration.isOnlyNote() || configuration.isBothNoteRest()) {
            //key
        for (int i = 0; i < r.nextInt(number_elements) + 1; i++) {
            Element key = g_element.generate_random_key(doc);
            key.appendChild(g_element.generate_random_tablature_pitch(doc));
            if (isGenerateElement()) key.appendChild(g_element.generate_random_tablature_articulation(doc));
            if (isGenerateElement()) key.appendChild(g_element.generate_random_tie(doc));
            if (isGenerateElement()) key.appendChild(g_element.generate_random_tablature_fingering(doc));
            tablature_symbol.appendChild(key);
        }
        }

    }

    private void append_children_to_chord(Document doc, Element chord, Element root) {
        // <!ELEMENT chord (duration, augmentation_dots?, (notehead+ | repetition), articulation?)>

        // duration
        Element duration = g_element.generate_random_duration(doc);
        chord.appendChild(duration);

        if (isGenerateElement()) {
            Element argumentation_dots = g_element.generate_random_augmentation_dots(doc);
            chord.appendChild(argumentation_dots);
        }

        if (isGenerateElement()) {
            for (int i = 0; i < r.nextInt(number_elements) + 2; i++) {
                if(configuration.isOnlyNote() || configuration.isBothNoteRest()) {
                    Element notehead = g_element.generate_random_notehead(doc);
                    append_children_to_notehead(doc, notehead, root);
                    chord.appendChild(notehead);
                }
            }
                // notehead

        } else {
            // repetition
            Element repetition = g_element.generate_random_repetition(doc);
            chord.appendChild(repetition);
        }

        Element articulation = g_element.generate_random_articulation(doc);
        chord.appendChild(articulation);
    }

    private void append_children_to_gregorian_sybomol(Document doc, Element gregorian_symbol, Element root) {
        for (int i = 0; i < r.nextInt(50 + number_elements) + number_elements; i++) {
            // notehead
            //<!ELEMENT notehead (pitch, printed_accidentals?, tie?, fingering?)>
            Element notehead = g_element.generate_random_notehead(doc);
            notehead.appendChild(g_element.generate_random_pitch(doc));
            if (isGenerateElement()) notehead.appendChild(g_element.generate_random_printed_accidentals(doc));
            if (isGenerateElement()) notehead.appendChild(g_element.generate_random_tie(doc));
            if (isGenerateElement()) notehead.appendChild(g_element.generate_random_fingering(doc));
            gregorian_symbol.appendChild(notehead);
        }
    }

    private void append_children_to_part(Document doc, Element part, Element root) {
        // <!ELEMENT part (voice_list, measure+)>

        //voice list
        Element voice_list = g_element.generate_random_voice_list(doc);
        for (int i = 0; i < r.nextInt(number_elements) + 1; i++) {
            Element voice_item = g_element.generate_random_voice_item(doc);
            g_element.voice_items.add(voice_item.getAttribute("id"));
            voice_list.appendChild(voice_item);
        }
        part.appendChild(voice_list);

        // <!ELEMENT measure (voice+ | multiple_rest | measure_repeat?)>
        for(int m = 0; m < configuration.getLenght(); m++){
            Element measure = g_element.generate_random_measure(doc);
            int choice1 = r.nextInt(3);
            if (choice1 == 0) {

                    for (int i = 0; i < r.nextInt(number_elements + 5) + 5; i++) {
                        Element voice = g_element.generate_random_voice(doc);
                        g_element.voices.add(voice.getAttribute("id"));
                        // <!ELEMENT voice (chord | rest | tablature_symbol | gregorian_symbol)+>
                        int choice2 = r.nextInt(4);
                        //choice2 = 0;
                        switch (choice2) {
                            case 0: // chord
                                if (configuration.isOnlyNote() || configuration.isBothNoteRest()) {
                                    Element chord = g_element.generate_random_chord(doc);
                                    //System.out.println(root.getElementsByTagName("spine").getLength());
                                    append_children_to_chord(doc, chord, root);
                                    voice.appendChild(chord);
                                }
                                break;
                            case 1: // rest
                                if (configuration.isOnlyRest() || configuration.isBothNoteRest()) {
                                    Element rest = g_element.generate_random_rest(doc);
                                    append_children_to_rest(doc, rest, root);
                                    voice.appendChild(rest);
                                }
                                break;
                            case 2: // tablature-symbol
                                Element tablature_symbol = g_element.generate_random_tablature_symbol(doc);
                                append_children_to_tablature_symbol(doc, tablature_symbol, root);
                                voice.appendChild(tablature_symbol);
                                break;
                            case 3: // gregorian-symbol
                                if (configuration.isOnlyNote() || configuration.isBothNoteRest()) {
                                    Element gregoryan_syboml = g_element.generate_random_gregorian_symbol(doc);
                                    append_children_to_gregorian_sybomol(doc, gregoryan_syboml, root);
                                    voice.appendChild(gregoryan_syboml);
                                }
                                break;
                            default:
                                System.out.println("Errore nella creazione dei figli...");
                        }
                        measure.appendChild(voice);
                    }
            } else if (choice1 == 1) measure.appendChild(g_element.generate_random_multiple_rest(doc));
            else {
                if (isGenerateElement()) measure.appendChild(g_element.generate_random_measure_repeat(doc));
            }
            part.appendChild(measure);
        }
    }

    private void append_children_to_los(Document doc, Element los, Element root) {
        // <!ELEMENT los (agogics*, text_field*, metronomic_indication*, staff_list, part+, horizontal_symbols?, ornaments?, lyrics*)>
        for (int i = 0; i < r.nextInt(number_elements) + 1; i++) los.appendChild(g_element.generate_random_agogics(doc));
        for (int i = 0; i < r.nextInt(number_elements) + 1; i++) los.appendChild(g_element.generate_random_text_field(doc));
        for (int i = 0; i < r.nextInt(number_elements) + 1; i++)
            los.appendChild(g_element.generate_random_metronomic_indication(doc));

        Element staff_list = g_element.generate_random_staff_list(doc);
        append_children_to_staff_list(doc, staff_list, root);
        los.appendChild(staff_list);


        for (int i = 0; i < r.nextInt(number_elements) + 1; i++) {
            Element part = g_element.generate_random_part(doc);
            g_element.parts.add(part.getAttribute("id"));
            append_children_to_part(doc, part, root);
            los.appendChild(part);
        }

        los.appendChild(g_element.generate_random_horizontal_symbols(doc));
        los.appendChild(g_element.generate_random_ornaments(doc));
        for (int i = 0; i < r.nextInt(number_elements) + 1; i++) los.appendChild(g_element.generate_random_lyrics(doc));
    }

    private void append_children_to_page(Document doc, Element page, Element root) {
        // <!ELEMENT page ((standard_page_format | custom_page_format), layout_system*, layout_images*, layout_shapes*)>

        // (standard_page_format | custom_page_format)
        if (isGenerateElement()) page.appendChild(g_element.generate_random_standard_page_format(doc));
        else page.appendChild(g_element.generate_random_custom_page_format(doc));

        // layout_system
        for (int i = 0; i < r.nextInt(number_elements); i++) {
            Element layout_system = g_element.generate_random_layout_system(doc);
            for (int j = 0; j < r.nextInt(number_elements) + 1; j++) {
                layout_system.appendChild(g_element.generate_random_layout_staff(doc));
            }
            page.appendChild(layout_system);
        }

        for (int i = 0; i < r.nextInt(number_elements); i++) page.appendChild(g_element.generate_random_layout_images(doc));
        for (int i = 0; i < r.nextInt(number_elements); i++) page.appendChild(g_element.generate_random_layout_shapes(doc));

    }

    private void append_children_to_layout(Document doc, Element layout, Element root) {
        // <!ELEMENT layout (page+, text_font?, music_font?)>

        // page
        for (int i = 0; i < r.nextInt(5) + 1; i++) {
            Element page = g_element.generate_random_page(doc);
            append_children_to_page(doc, page, root);
            layout.appendChild(page);
        }

        // text_font
        if (isGenerateElement()) layout.appendChild(g_element.generate_random_text_font(doc));

        //music_font
        if (isGenerateElement()) layout.appendChild(g_element.generate_random_music_font(doc));
    }

    private void append_children_to_midi_mapping(Document doc, Element midi_mapping, Element root) {
        // <!ELEMENT midi_mapping (midi_event_sequence+)>

        for (int i = 0; i < r.nextInt(number_elements) + 1; i++) {
            Element midi_event_sequence = g_element.generate_random_midi_event_sequence(doc);
            if (isGenerateElement()) {
                for (int j = 0; j < r.nextInt(number_elements) + 1; j++)
                    midi_event_sequence.appendChild(g_element.generate_random_midi_event(doc));
            } else {
                for (int j = 0; j < r.nextInt(number_elements) + 1; j++)
                    midi_event_sequence.appendChild(g_element.generate_random_sys_ex(doc));
            }
            midi_mapping.appendChild(midi_event_sequence);
        }
    }

    private void append_children_to_midi_instance(Document doc, Element midi_instance, Element root) {
        //<!ELEMENT midi_instance (midi_mapping+, rights?)>

        //midi_mapping
        for (int j = 0; j < r.nextInt(number_elements) + 1; j++) {
            Element midi_mapping = g_element.generate_random_midi_mapping(doc);
            append_children_to_midi_mapping(doc, midi_mapping, root);
            midi_instance.appendChild(midi_mapping);
        }

        if (isGenerateElement()) midi_instance.appendChild(g_element.generate_random_rights(doc));

    }

    private void append_children_to_c_sound_instance(Document doc, Element c_sound_instance, Element root) {
        // <!ELEMENT csound_instance (csound_score | csound_orchestra)+>
        for (int i = 0; i < r.nextInt(number_elements) + 1; i++) {
            if (isGenerateElement()) {
                //csound_score
                Element csound_score = g_element.generate_random_csound_score(doc);
                // <!ELEMENT csound_score (csound_spine_event+, rights?)>
                for (int j = 0; j < r.nextInt(number_elements) + 1; j++)
                    csound_score.appendChild(g_element.generate_random_csound_spine_event(doc));
                if (isGenerateElement()) csound_score.appendChild(g_element.generate_random_rights(doc));
                c_sound_instance.appendChild(csound_score);
            } else {
                //csound_orchestra
                Element csound_orchestra = g_element.generate_random_csound_orchestra(doc);
                // <!ELEMENT csound_orchestra (csound_instrument_mapping*, rights?)>
                for (int j = 0; j < r.nextInt(number_elements); j++) {
                    Element csound_instrument_mapping = g_element.generate_random_csound_instrument_mapping(doc);
                    // <!ELEMENT csound_instrument_mapping (csound_part_ref | csound_spine_ref)+>
                    if (isGenerateElement()) {
                        for (int k = 0; k < r.nextInt(number_elements) + 1; k++)
                            csound_instrument_mapping.appendChild(g_element.generate_random_csound_part_ref(doc));
                    } else {
                        for (int k = 0; k < r.nextInt(number_elements) + 1; k++)
                            csound_instrument_mapping.appendChild(g_element.generate_random_csound_spine_ref(doc));
                    }
                    csound_orchestra.appendChild(csound_instrument_mapping);
                }
                if (isGenerateElement()) csound_orchestra.appendChild(g_element.generate_random_rights(doc));
                c_sound_instance.appendChild(csound_orchestra);
            }
        }
    }

    private void append_children_to_mpeg4_instance(Document doc, Element mpeg4_instance, Element root) {
        // <!ELEMENT mpeg4_instance (mpeg4_score | mpeg4_orchestra)+
        for (int i = 0; i < r.nextInt(number_elements) + 1; i++) {
            if (isGenerateElement()) {
                // mpeg4_score
                Element mpeg4_score = g_element.generate_random_mpeg4score(doc);
                // <!ELEMENT mpeg4_score (csound_spine_event+, rights?)>
                for (int j = 0; j < r.nextInt(number_elements) + 1; j++)
                    mpeg4_score.appendChild(g_element.generate_random_csound_spine_event(doc));
                if (isGenerateElement()) mpeg4_score.appendChild(g_element.generate_random_rights(doc));
                mpeg4_instance.appendChild(mpeg4_score);
            } else {
                // mpeg4_orchestra
                // <!ELEMENT mpeg4_orchestra (mpeg4_instrument_mapping*, rights?)>
                Element mpeg4_orchestra = g_element.generate_random_mpeg4_orchestra(doc);

                for (int j = 0; j < r.nextInt(number_elements) + 1; j++) {
                    Element mpeg4_instrument_mapping = g_element.generate_random_mpeg4_instrument_mapping(doc);
                    //<!ELEMENT mpeg4_instrument_mapping (mpeg4_part_ref | mpeg4_spine_ref)+>
                    for (int k = 0; k < r.nextInt(number_elements) + 1; k++) {
                        if (isGenerateElement()) {
                            mpeg4_instrument_mapping.appendChild(g_element.generate_random_mpeg4_part_ref(doc));
                        } else {
                            mpeg4_instrument_mapping.appendChild(g_element.generate_random_mpeg4_spine_ref(doc));
                        }
                    }
                    mpeg4_orchestra.appendChild(mpeg4_instrument_mapping);
                }
                if (isGenerateElement()) mpeg4_orchestra.appendChild(g_element.generate_random_rights(doc));
                mpeg4_instance.appendChild(mpeg4_orchestra);
            }
        }

    }

    private void append_children_to_performance(Document doc, Element performance, Element root) {
        // <!ELEMENT performance (midi_instance | csound_instance | mpeg4_instance)+>
        int choice = r.nextInt(3);
        for (int i = 0; i < r.nextInt(number_elements - 3) + 2; i++) {
            switch (choice) {
                case 0:
                    // midi_instance
                    Element midi_instance = g_element.generate_random_midi_instance(doc);
                    append_children_to_midi_instance(doc, midi_instance, root);
                    performance.appendChild(midi_instance);
                    break;
                case 1:
                    // csound_instance
                    Element c_sound_instance = g_element.generate_random_csound_instance(doc);
                    append_children_to_c_sound_instance(doc, c_sound_instance, root);
                    performance.appendChild(c_sound_instance);
                    break;
                case 2:
                    // mpeg4_instance
                    Element mpeg4_instance = g_element.generate_random_mpeg4_instance(doc);
                    append_children_to_mpeg4_instance(doc, mpeg4_instance, root);
                    performance.appendChild(mpeg4_instance);
                    break;
                default:
                    System.out.println("Errore nella creazione dei figli...");
            }
        }

    }

    private void append_children_to_logic(Element logic, Element root, Document doc) {
        // <!ELEMENT logic (spine, los?, layout?)>

        // spine
        Element spine = g_element.generate_random_spine(doc);
        for (int i = 0; i < configuration.getNumber_struments(); i++) {
            Element event = g_element.generate_random_event(doc);
            g_element.events.add(event.getAttribute("id"));
            g_element.domains.add(event.getAttribute("id"));
            g_element.codomains.add(event.getAttribute("id"));
            spine.appendChild(event);
        }
        logic.appendChild(spine);


        // los
        //if(isGenerateElement()) {
        Element los = g_element.generate_random_los(doc);
        append_children_to_los(doc, los, root);
        logic.appendChild(los);
        //}

        // layout
        if (isGenerateElement()) {
            Element layout = g_element.generate_random_layout(doc);
            append_children_to_layout(doc, layout, root);
            logic.appendChild(layout);
        }

    }

    private void append_children_to_segment(Document doc, Element segment, Element root) {
        //  <!ELEMENT segment (segment_event+, feature_object*)>

        for (int i = 0; i < r.nextInt(number_elements) + 1; i++)
            segment.appendChild(g_element.generate_random_segment_event(doc));
        for (int i = 0; i < r.nextInt(number_elements); i++) {
            Element fo = g_element.generate_feature_object(doc);
            fo.appendChild(g_element.generate_random_simple_description(doc));
            segment.appendChild(fo);
        }

    }

    private void append_children_to_analysis(Document doc, Element analysis, Element root) {
        // <!ELEMENT analysis (segmentation, relationships?, feature_object_relationships?)>

        //segmentation
        Element segmentation = g_element.generate_random_segmentation(doc);
        for (int i = 0; i < r.nextInt(number_elements) + 1; i++) {
            Element segment = g_element.generate_random_segment(doc);
            append_children_to_segment(doc, segment, root);
            g_element.segments.add(segment.getAttribute("id"));
            segmentation.appendChild(segment);
        }

        analysis.appendChild(segmentation);

        //relationships
        if (isGenerateElement()) {
            Element relationships = g_element.generate_random_relationships(doc);
            analysis.appendChild(relationships);
        }


        // feature_object_relationships
        //if (isGenerateElement()) {
        Element feature_object_relationships = g_element.generate_random_feature_object_relationships(doc);
        analysis.appendChild(feature_object_relationships);
        //}
    }

    private void append_children_to_petri_nets(Document doc, Element petri_nets, Element root) {
        //<!ELEMENT petri_net (place | transition)+>
        for (int i = 0; i < r.nextInt(number_elements) + 1; i++) {
            Element petri_net = g_element.generate_random_petri_net(doc);

            if (isGenerateElement()) {
                for (int k = 0; k < r.nextInt(number_elements) + 1; k++) {
                    petri_net.appendChild(g_element.generate_random_place(doc));
                }
            } else {
                for (int k = 0; k < r.nextInt(number_elements) + 1; k++) {
                    petri_net.appendChild(g_element.generate_random_transition(doc));
                }
            }

            petri_nets.appendChild(petri_net);
        }
    }

    private void append_children_to_mir(Document doc, Element mir, Element root) {
        for (int k = 0; k < r.nextInt(number_elements) + 1; k++) {
            Element mir_model = g_element.generate_random_mir_model(doc);

            // mir_object
            for (int j = 0; j < r.nextInt(number_elements) + 1; j++) {
                Element mir_object = g_element.generate_random_mir_object(doc);

                // mir_subobject
                for (int i = 0; i < r.nextInt(number_elements) + 1; i++) {
                    Element mir_subobject = g_element.generate_random_mir_subobject(doc);
                    mir_object.appendChild(mir_subobject);
                }

                // mir_feature
                for (int i = 0; i < r.nextInt(number_elements) + 1; i++) {
                    Element mir_feature = g_element.generate_random_mir_feature(doc);
                    mir_object.appendChild(mir_feature);
                }
                mir_model.appendChild(mir_object);
            }

            // mir_morphism
            for (int j = 0; j < r.nextInt(number_elements); j++) {
                Element mir_morphism = g_element.generate_random_mir_morphism(doc);
                for (int i = 0; i < r.nextInt(number_elements) + 1; i++) {
                    Element mir_subobject = g_element.generate_random_mir_subobject(doc);
                    Element mir_feature = g_element.generate_random_mir_feature(doc);
                    // da confermare
                    //domains.add((Element) deepCopy(mir_feature)); codomains.add((Element) deepCopy(mir_feature));
                    mir_morphism.appendChild(mir_feature);
                }
                mir_model.appendChild(mir_morphism);
            }
            mir.appendChild(mir_model);
        }
    }

    private void append_children_to_structural(Document doc, Element structural, Element root) {
        // <!ELEMENT structural (chord_grid*, analysis*, petri_nets*, mir*)>

        // chord_grid
        for (int i = 0; i < r.nextInt(number_elements) + 1; i++) {
            Element chord_grid = g_element.generate_random_chord_grid(doc);

            structural.appendChild(chord_grid);
        }

        // analysis
        for (int i = 0; i < r.nextInt(number_elements) + 1; i++) {
            Element analysis = g_element.generate_random_analysis(doc);
            append_children_to_analysis(doc, analysis, root);
            structural.appendChild(analysis);
        }

        // petri_nets
        for (int i = 0; i < r.nextInt(number_elements) + 1; i++) {
            Element petri_nets = g_element.generate_random_petri_nets(doc);
            append_children_to_petri_nets(doc, petri_nets, root);
            structural.appendChild(petri_nets);
        }

        // mir
        for (int i = 0; i < r.nextInt(number_elements) + 1; i++) {
            Element mir = g_element.generate_random_mir(doc);
            append_children_to_mir(doc, mir, root);
            structural.appendChild(mir);

        }

    }


    private void append_children_to_general(Element general, Element root, Document doc) {

        // analog_media
        Element analog_media = g_element.generate_random_analog_media(doc);
        for (int i = 0; i < r.nextInt(number_elements) + 1; i++)
            analog_media.appendChild(g_element.generate_random_analog_medium(doc));

        // notes
        Element notes = g_element.generate_random_notes(doc);

        // related_files
        Element related_files = g_element.generate_random_related_files(doc);


        // description
        Element description = g_element.generate_random_description(doc);
        append_children_to_description(description, doc);

        general.appendChild(description);
        if (isGenerateElement()) general.appendChild(related_files);
        if (isGenerateElement()) general.appendChild(analog_media);
        if (isGenerateElement()) general.appendChild(notes);
    }


    private void append_children_to_description(Element description, Document doc) {
        // main_title
        description.appendChild(g_element.generate_random_main_title(doc));

        // author/s
        if (isGenerateElement()) {
            for (int i = 0; i < r.nextInt(number_elements); i++) description.appendChild(g_element.generate_random_author(doc));
        }

        // append other_title/s
        if (isGenerateElement()) {
            for (int i = 0; i < r.nextInt(number_elements); i++)
                description.appendChild(g_element.generate_random_other_title(doc));
        }

        // append date/s
        if (isGenerateElement()) {
            for (int i = 0; i < r.nextInt(number_elements); i++) description.appendChild(g_element.generate_random_date(doc));
        }

        // append genres
        if (isGenerateElement()) description.appendChild(g_element.generate_random_genres(doc));

    }


    private boolean isGenerateElement() {
        return r.nextBoolean();
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
            t.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "C:\\Users\\matti\\Desktop\\RandomGeneratorIEEE1599\\ieee1599.dtd");
            t.transform(input, output);
        } catch (TransformerException e) {
            System.out.println("Errore durante il salvataggio...");
        }
    }

}