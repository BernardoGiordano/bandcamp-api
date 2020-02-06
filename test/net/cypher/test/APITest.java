package net.cypher.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import io.laniakia.core.BandcampAPI;
import io.laniakia.domain.Album;
import io.laniakia.domain.Band;
import io.laniakia.util.APICall;
import io.laniakia.util.ConfigUtil;
import io.laniakia.util.JSONUtil;

public class APITest 
{
	@Test
	public void testConfigFile() throws Exception
	{
		APICall query = ConfigUtil.getConfigValue("search");
		assertEquals("search", query.getName());
		assertEquals(1, query.getParameters().size());
	}
	
	@Test
	public void testSearchSingle() throws Exception
	{
		BandcampAPI b = new BandcampAPI();
		List<Band> bandList = b.searchBand("parallels", true, false);
		for(Band band : bandList)
		{
			assertTrue(band != null);
		}
	}
	
//	@Test
//	public void testSearchMulti() throws Exception
//	{
//		BandcampAPI b = new BandcampAPI(2);
//		List<Band> bandList = b.searchBand("parallels", true, true);
//		for(Band band : bandList)
//		{
//			assertTrue(band != null);
//			logger.debug("Band Name: "  + band.getName() + ", Band URL: " + band.getUrl() + ", Albums: " + band.getAlbums().size());
//		}
//		logger.debug("Band search results size: " + bandList.size());
//	}
	
	@Test
	public void testBandParse() throws Exception
	{
		JsonObject p = JSONUtil.parseBandData("http://parallels.bandcamp.com/music");
		Band b = new Gson().fromJson(p.toString(), Band.class);
		assertTrue(b != null);
		assertEquals("Parallels", b.getName());
	}
	
	@Test
	public void testMetadata() throws Exception
	{
		BandcampAPI b = new BandcampAPI();
		Band band = b.getBand("http://parallels.bandcamp.com/");
		assertTrue(band.getTrackList().size() != 0);
	}
	
	@Test
	public void testSingleTracks() throws Exception
	{
		BandcampAPI b = new BandcampAPI();
		Band band = b.getBand("https://thaddeusdavid.bandcamp.com");
		assertTrue(band.getTrackList().size() > 0);
	}
	
	@Test
	public void testGetAlbum() throws Exception
	{
		BandcampAPI b = new BandcampAPI();
		Album album = b.getAlbum("https://parallels.bandcamp.com/album/visionaries");
		assertTrue(album != null);
		album = b.getAlbum("parallels", "visionaries");
		assertTrue(album != null);
	}
	
	@Test
	public void testGetAlbumWithNoTrackInfoProvided() throws Exception
	{
		BandcampAPI b = new BandcampAPI();
		Album album = b.getAlbum("https://ghostbath.bandcamp.com/album/moonlover");
		assertTrue(album != null);
	}
}
