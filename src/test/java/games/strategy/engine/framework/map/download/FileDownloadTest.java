package games.strategy.engine.framework.map.download;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import games.strategy.engine.framework.map.download.DownloadFile.DownloadState;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class FileDownloadTest {
  @Mock
  private DownloadFileDescription mockDownload;

  @Test
  public void testBasicStartCancel() throws Exception {
    final DownloadFile testObj = new DownloadFile(mockDownload, mock(DownloadListener.class));
    assertThat(testObj.getDownloadState(), is(DownloadState.NOT_STARTED));

    testObj.startAsyncDownload();
    assertThat(testObj.getDownloadState(), is(DownloadState.DOWNLOADING));

    testObj.cancelDownload();
    assertThat(testObj.getDownloadState(), is(DownloadState.CANCELLED));
  }
}
