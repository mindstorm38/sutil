package io.sutil.lang;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.sutil.StringUtils;
import io.sutil.resource.Resource;
import io.sutil.resource.ResourceAccessor;

import static io.sutil.LoggerUtils.*;

public class LanguageManager {
	
	// Constants \\
	
	public static final Pattern LANG_IDENTIFIER_PATTERN			= Pattern.compile("#identifier (.+)");
	public static final Pattern LANG_NAME_PATTERN				= Pattern.compile("#name (.+)");
	public static final Pattern LANG_ENTRY_PATTERN				= Pattern.compile("^([a-zA-Z0-9_.-]+)=(.+)$");
	public static final String LANG_DEFAULT_TOKEN				= "#default";
	
	// Class \\
	
	private final ResourceAccessor resourceAccessor;
	private final List<String> langsFolderPaths;
	private Charset charset;
	
	private final Map<String, Language> availableLanguages = new HashMap<>();
	
	private Language defaultLanguage = null;
	private Language currentLanguage = null;
	
	public LanguageManager(ResourceAccessor resourceAccessor, String baseLangsFolder) {
		
		this.resourceAccessor = resourceAccessor;
		this.langsFolderPaths = new ArrayList<>();
		this.langsFolderPaths.add( baseLangsFolder );
		this.charset = StringUtils.CHARSET_UTF_8;
		
	}
	
	public List<String> getLangsFoldersPath() { return this.langsFolderPaths; }
	
	public void addLangFolderPath(String path) {
		this.langsFolderPaths.add( path );
	}
	
	public void setCharset(Charset charset) {
		this.charset = charset;
	}
	
	public void init() throws Exception {
		
		this.updateAvailableLanguages();
		
		if ( this.availableLanguages.size() == 0 )
			throw new Exception( "No languages detected !" );
		
		if ( this.defaultLanguage == null ) this.defaultLanguage = new ArrayList<>( this.availableLanguages.values() ).get( 0 );
		
		this.loadLanguage( this.defaultLanguage );
		
	}
	
	public void updateAvailableLanguages() {
		
		this.availableLanguages.clear();
		
		for ( String langFolderPath : this.langsFolderPaths )
			this.resourceAccessor.listResources( langFolderPath ).forEach( this::parseLanguage );
		
	}
	
	private void parseLanguage(Resource resource) {
		
		try {
			
			BufferedReader reader = new BufferedReader( new InputStreamReader( resource.getInputStream(), this.charset ) );
			
			String name = null;
			String identifier = null;
			boolean def = false;
			
			String line = null;
			
			while ( ( line = reader.readLine() ) != null ) {
				
				Matcher matcherName = LANG_NAME_PATTERN.matcher( line );
				
				if ( matcherName.find() ) {
					
					name = matcherName.group(1);
					continue;
					
				}
				
				Matcher matcherIdentifier = LANG_IDENTIFIER_PATTERN.matcher( line );
				
				if ( matcherIdentifier.find() ) {
					
					identifier = matcherIdentifier.group(1);
					continue;
					
				}
				
				if ( line.contains( LANG_DEFAULT_TOKEN ) ) def = true;
				
			}
			
			reader.close();
			
			if ( identifier == null ) {
				
				LOGGER.warning( "Unable to find language identifier for '" + resource.getPath() + "'" );
				
			} else {
				
				if ( name == null ) name = identifier;
				
				Language language = new Language( resource.getPath(), identifier, name );
				if ( def ) this.defaultLanguage = language;
				this.availableLanguages.put( identifier, language );
				
			}
			
		} catch (IOException e) {
			LOGGER.log( Level.WARNING, "Unable to preload '" + resource.getPath() + "' language", e );
		}
		
	}
	
	public Map<String, Language> getAvailableLanguages() {
		return this.availableLanguages;
	}
	
	private void loadLanguage(Language language) {
		
		if ( language == null ) throw new NullPointerException();
		
		try {
			
			BufferedReader reader = new BufferedReader( new InputStreamReader( this.resourceAccessor.resourceInputStream( language.getPath() ), this.charset ) );
			
			language.entries.clear();
			
			String line = null;
			
			while ( ( line = reader.readLine() ) != null ) {
				
				Matcher matcherEntry = LANG_ENTRY_PATTERN.matcher( line );
				
				if ( matcherEntry.find() ) {
					language.entries.put( matcherEntry.group(1), matcherEntry.group(2) );
				}
				
			}
			
			this.currentLanguage = language;
			
		} catch (IOException e) {
			throw new IllegalStateException( "Unable to load language '" + language.getIdentifier() + "'", e );
		}
		
	}
	
	public void loadLanguage(String identifier) {
		Language language = this.availableLanguages.get( identifier );
		if ( language == null ) throw new IllegalStateException( "Unable to load language '" + identifier + "'" );
		this.loadLanguage( language );
	}
	
	public String getEntry(String key, Object...params) {
		if ( this.currentLanguage == null ) return key;
		String entry = this.currentLanguage.getEntry( key );
		if ( entry == null ) return key;
		for ( int i = 0; i < params.length; i++ ) {
			entry = entry.replaceAll( Pattern.quote( "{" + i + "}" ), params[i].toString() );
		}
		return entry;
	}
	
}
