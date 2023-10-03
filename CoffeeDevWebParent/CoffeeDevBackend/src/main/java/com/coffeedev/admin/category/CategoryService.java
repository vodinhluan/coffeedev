package com.coffeedev.admin.category;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.coffeedev.common.entity.Category;


@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repo;

	public List<Category> listAll() {
		List<Category> rootCategories = repo.findRootCategories();
		return listHierarchicalCategories(rootCategories);
	}

	private List<Category> listHierarchicalCategories(List<Category> rootCategories) {
		List<Category> hierarchicalCategories = new ArrayList<>();

		for (Category rootCategory : rootCategories) {
			hierarchicalCategories.add(Category.copyFull(rootCategory));

			Set<Category> children = rootCategory.getChildren();

			for (Category subCategory : children) {
				String name = "--"+ subCategory.getName();				
				hierarchicalCategories.add(Category.copyFull(subCategory, name));

				listSubHierarchicalCategories(hierarchicalCategories, subCategory, 1);
			}
		}
		return hierarchicalCategories;
	}

	private void listSubHierarchicalCategories(List<Category> hierarchicalCategories ,
			Category parent, int subLevel) {
		Set<Category> children = parent.getChildren();
		int newSubLevel = subLevel + 1;

		for (Category subCategory : children) {
			String name = "";
			for (int i = 0; i < newSubLevel; i++) {				
				name += "--";
			}
			name += subCategory.getName();
			hierarchicalCategories.add(Category.copyFull(subCategory, name));

			listSubHierarchicalCategories(hierarchicalCategories, subCategory, newSubLevel);
		}
	}

	public Category save(Category category) {
		return repo.save(category);
	}

//	public void updateCategoryEnabledStatus(Integer id, boolean enabled) {
//		repo.updateEnabledStatus(id, enabled);	
//	}

	public List<Category> listCategoriesUsedInform() {
		List<Category> categoriesUsedInform = new ArrayList();
		Iterable<Category> categoriesInDb = repo.findAll();

		for (Category category : categoriesInDb) {
			if (category.getParent() == null) {
				categoriesUsedInform.add(Category.copyIdandName(category));
				System.out.println(category.getName());
				Set<Category> children = category.getChildren();

				for (Category subCategory : children) {
					String name = "--"+ subCategory.getName();
					categoriesUsedInform.add(Category.copyIdandName(subCategory.getId(), name));
					listSubCategoriesUsedInform(categoriesUsedInform, subCategory, 1);
				}
			}

		}

		return categoriesUsedInform;		
	}
	private void listSubCategoriesUsedInform(List<Category> categoriesUsedInform, Category parent, int subLevel) {
		int newSubLevel = subLevel + 1;
		Set<Category> children = parent.getChildren();

		for (Category subCategory : children) {
			String name = "";
			for (int i = 0; i < newSubLevel; i++) {				
				name += "--";
			}

			name += subCategory.getName();

			categoriesUsedInform.add(Category.copyIdandName(subCategory.getId(), name));

			listSubCategoriesUsedInform(categoriesUsedInform, subCategory, newSubLevel);
		}		
	}

	// Edit category
		public Category get(Integer id) throws CategoryNotFoundException {
			try {
				return repo.findById(id).get();
			} catch (NoSuchElementException ex) {
				throw new CategoryNotFoundException("Không thể tìm thấy loại sản phẩm với ID: "+ id);
			}
		}

		// Delete category
		public void delete(Integer id) throws CategoryNotFoundException {
			Long countById =  repo.countById(id);
			if (countById == null || countById == 0) {
				throw new CategoryNotFoundException("Không thể tìm thấy loại sản phẩm với ID: "+ id);
			}
			repo.deleteById(id);
		}

		public void updateCategoryEnabledStatus(Integer id, boolean enabled) {
			repo.updateEnabledStatus(id, enabled);
		}

		public String checkUnique(Integer id, String name) {
			boolean isCreatingNew = (id == null || id == 0);
			Category categoryName = repo.findByName(name);

			if (isCreatingNew) {
				if (categoryName != null)
				{
					return "DuplicateName";
				} else if (categoryName != null && categoryName.getId() != id) 
					return "DuplicateName";
			}
			return "OK";
		}

}

