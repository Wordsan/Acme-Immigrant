package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.FolderRepository;
import domain.Folder;

@Component
@Transactional
public class StringToFolderConverter implements Converter<String, Folder> {

	@Autowired
	FolderRepository folderRepository;

	@Override
	public Folder convert(final String source) {
		Folder result;
		int id;

		try {
			if (StringUtils.isEmpty(source))
				result = null;
			else {
				id = Integer.valueOf(source);
				result = this.folderRepository.findOne(id);
			}
		} catch (final Throwable ops) {
			throw new IllegalArgumentException(ops);
		}
		return result;
	}

}
